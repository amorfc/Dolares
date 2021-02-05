package com.example.dolares.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.dolares.R
import com.example.dolares.data.local.dao.LaunchesDao
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.data.local.model.launch.LaunchToNotify
import com.example.dolares.ui.MainActivity
import com.example.dolares.util.CHANNEL_ID
import com.example.dolares.util.NOTIFY_ID
import com.example.dolares.util.SECONDS_IN_15_MINUTES
import com.example.dolares.util.USER_LAUNCH_NOTIFICATION_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class NotificationLaunchesWorker(private val ctx:Context, params: WorkerParameters) :CoroutineWorker(ctx,params),KoinComponent{

    private val launchesDao: LaunchesDao by inject()

    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {

            val launches = getAllLaunchesFromDb()

            val allLaunchesToNotifyFromTable = getAllLaunchesToNotifyFromDb()

            if (launches.isNullOrEmpty() || allLaunchesToNotifyFromTable.isNullOrEmpty()) Result.retry()

            val allLaunchesToNotify:List<Launch> = usersNotifyLaunches(launches,allLaunchesToNotifyFromTable).sortedBy { it.dateUnix }



            val firstUserUpcomingLaunch: Launch = allLaunchesToNotify[0]

            val nextLaunchTime: Long = firstUserUpcomingLaunch.dateUnix ?: 0L

            if (nextLaunchTime == 0L || firstUserUpcomingLaunch.name == null || firstUserUpcomingLaunch.name!!.isBlank()) Result.retry()

            val currentTime = System.currentTimeMillis() / 1000

            if (nextLaunchTime - currentTime < SECONDS_IN_15_MINUTES) {

                val inMinutes = (nextLaunchTime - currentTime)/60

                fireNotification(firstUserUpcomingLaunch.flightNumber, inMinutes, firstUserUpcomingLaunch.name,firstUserUpcomingLaunch.id)

                Log.i(USER_LAUNCH_NOTIFICATION_TAG, "User Launch Notification Fired")

            }
                Result.success()
        }

    private fun fireNotification(flightNumber: Int?, inMinutes: Long, name: String?,launchId:String) {

        val pendingIntent = tapActionBuilder(launchId)

        val title = "New Rocket Launch $name"
        val content ="In $inMinutes minutes there is a new Launch.Flight Number: ${flightNumber.toString()}"

        val builder = NotificationCompat.Builder(ctx, CHANNEL_ID)
            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_focused)
            .setContentTitle(title)
            .setContentIntent(pendingIntent)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager: NotificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(notificationManager)
        notificationManager.notify(NOTIFY_ID,builder.build())
    }

    private suspend fun getAllLaunchesFromDb():List<Launch>{
        val currentTime = System.currentTimeMillis() / 1000

        return launchesDao.getLaunchesAfterNow(currentTime).sortedBy { it.dateUnix }
    }

    private fun getAllLaunchesToNotifyFromDb():List<LaunchToNotify>{
        val currentTime = System.currentTimeMillis() / 1000

        return launchesDao.getAllLaunchToNotify()
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

    private fun tapActionBuilder(launchId: String):PendingIntent{
        val args = Bundle()
        args.putString("launchId",launchId)
        return NavDeepLinkBuilder(applicationContext)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.launchDetailsFragment)
            .setArguments(args)
            .createPendingIntent()
    }
    private fun usersNotifyLaunches(apiLaunches:List<Launch>,notifyLaunches:List<LaunchToNotify>):List<Launch>{
        return apiLaunches.filter { notifyLaunches.map {launchToNotify ->  launchToNotify.id }.contains(it.id) }
    }
}