package com.example.dolares.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.dolares.R
import com.example.dolares.data.local.dao.LaunchesDao
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.ui.MainActivity
import com.example.dolares.util.CHANNEL_ID
import com.example.dolares.util.NOTIFY_ID
import com.example.dolares.util.SECONDS_IN24_HOUR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class NotificationLaunchesWorker(private val ctx:Context, params: WorkerParameters) :CoroutineWorker(ctx,params),KoinComponent{

    private val launchesDao: LaunchesDao by inject()

    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            var launches:List<Launch>  = getAllLaunchesFromDb()
            if (launches.isNullOrEmpty()) Result.retry()
            Log.i("TestDoWork", "firstCycle")
            val firstUpcomingLaunch: Launch = launches[0]
            val nextLaunchTime: Long = firstUpcomingLaunch.dateUnix ?: 0L

            if (nextLaunchTime == 0L || firstUpcomingLaunch.name == null || firstUpcomingLaunch.name.isBlank()) Result.retry()

            val currentTime = System.currentTimeMillis() / 1000
            if (nextLaunchTime - currentTime < SECONDS_IN24_HOUR) {
                fireNotification(firstUpcomingLaunch.flightNumber, nextLaunchTime, firstUpcomingLaunch.name)
            }
                Result.success()
        }

    private fun fireNotification(flightNumber: Int?, nextLaunchTime: Long, name: String?) {

        val pendingIntent = tapActionBuilder()

        val builder = NotificationCompat.Builder(ctx, CHANNEL_ID)
            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_focused)
            .setContentTitle("Launcehs")
            .setContentIntent(pendingIntent)
            .setContentText("Launches interval is 15 minutes")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager: NotificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(notificationManager)
        notificationManager.notify(NOTIFY_ID,builder.build())
    }

    suspend fun getAllLaunchesFromDb():List<Launch>{
        val currentTime = System.currentTimeMillis() / 1000

        return launchesDao.getLaunchesAfterNow(currentTime).sortedBy { it.dateUnix }
    }
    private fun createNotificationChannel(notificationManager: NotificationManager) {

        val name = "NotificationChannel"
        val descriptionText = "LaunchesNotification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }


        notificationManager.createNotificationChannel(channel)
    }

    private fun tapActionBuilder() = NavDeepLinkBuilder(applicationContext)
        .setComponentName(MainActivity::class.java)
        .setGraph(R.navigation. nav_graph)
        .setDestination(R.id.launchesFragment)
        .createPendingIntent()
}