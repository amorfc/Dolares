package com.example.dolares.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.dolares.R
import com.example.dolares.data.local.dao.LaunchesDao
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.ui.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*

class NotificationDailyLaunchesWorker (private val ctx: Context, params: WorkerParameters) :
    CoroutineWorker(ctx,params), KoinComponent {

    private val launchesDao:LaunchesDao  by inject()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        val currentTime = System.currentTimeMillis() / 1000

        val launches = launchesDao.getLaunchesAfterNow(currentTime)
            .sortedBy { it.dateUnix }

        if (launches.isNullOrEmpty()) Result.retry()
        val firstUpcomingLaunch: Launch = launches[0]
        val nextLaunchTime: Long = firstUpcomingLaunch.dateUnix ?: 0L
        val missionName: String = firstUpcomingLaunch.name ?: "Not Specified"
        val flightNumber: Int = firstUpcomingLaunch.flightNumber ?: 0

        if (nextLaunchTime == 0L || missionName.isBlank()) Result.retry()

        // check if next launch is in less than 24 hours
        if (nextLaunchTime - currentTime < NUMBER_OF_SECONDS_IN_24H) {
            triggerNotification(flightNumber, nextLaunchTime, missionName,firstUpcomingLaunch.id)
        }

        Result.success()
    }

    private fun triggerNotification(flightNumber: Int, nextLaunchTime: Long, missionName: String,launchId:String) {

        val args = Bundle()
        args.putString("launchId",launchId)

        val pendingIntent = NavDeepLinkBuilder(applicationContext)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.launchDetailsFragment)
            .setArguments(args)
            .createPendingIntent()

        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        simpleDateFormat.timeZone = Calendar.getInstance().timeZone
        val launchTimeLocal = simpleDateFormat.format(Date(nextLaunchTime * 1000))

        val contentText =
            applicationContext.getString(
                R.string.launch_notification_desc_template,
                missionName,
                launchTimeLocal
            )

        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.common_full_open_on_phone)
            .setContentTitle(
                applicationContext.resources.getString(
                    R.string.flight_number_template_in_less_than_24h,
                    flightNumber
                )
            )
            .setAutoCancel(true)
            .setContentText(contentText)
            .setStyle(NotificationCompat.BigTextStyle())
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val name = "NotificationDailyLaunchesChannel"
        val descriptionText = "Daily Launches Notification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    companion object {
        private const val NUMBER_OF_SECONDS_IN_24H = 86400
        private const val CHANNEL_ID = "launches_channel_01"
        private const val NOTIFICATION_ID = 100001
    }
}