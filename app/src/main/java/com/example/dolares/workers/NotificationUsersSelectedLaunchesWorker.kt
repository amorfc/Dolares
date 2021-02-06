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
import com.example.dolares.util.SECONDS_IN_15_MINUTES
import com.example.dolares.util.USERS_LAUNCHES_NOTIFY_ID
import com.example.dolares.util.USERS_LAUNCHES_WORKER_CHANNEL_ID
import com.example.dolares.util.USER_LAUNCH_NOTIFICATION_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*

class NotificationUsersSelectedLaunchesWorker(private val ctx: Context, params: WorkerParameters) :
    CoroutineWorker(ctx, params), KoinComponent {

    private val launchesDao: LaunchesDao by inject()

    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {

            val launches = getAllLaunchesFromDb()

            val allLaunchesToNotifyFromTable = getAllLaunchesToNotifyFromDb()

            if (launches.isNullOrEmpty() || allLaunchesToNotifyFromTable.isNullOrEmpty()) Result.retry()

            val allLaunchesToNotify: List<Launch> =
                usersNotifyLaunches(launches, allLaunchesToNotifyFromTable).sortedBy { it.dateUnix }

            if (allLaunchesToNotify.isNullOrEmpty()) {
                Result.success()
            } else {
                val firstUserUpcomingLaunch: Launch = allLaunchesToNotify.first()

                val nextLaunchTime: Long = firstUserUpcomingLaunch.dateUnix ?: 0L

                if (nextLaunchTime == 0L || firstUserUpcomingLaunch.name == null || firstUserUpcomingLaunch.name!!.isBlank()) Result.retry()

                val currentTime = System.currentTimeMillis() / 1000

                if (nextLaunchTime - currentTime < SECONDS_IN_15_MINUTES) {

                    fireNotification(
                        firstUserUpcomingLaunch.flightNumber,
                        nextLaunchTime,
                        firstUserUpcomingLaunch.name,
                        firstUserUpcomingLaunch.id
                    )

                    Log.i(USER_LAUNCH_NOTIFICATION_TAG, "User Launch Notification Fired")

                }
                Result.success()
            }
        }

    private fun fireNotification(
        flightNumber: Int?,
        nextLaunchTime: Long,
        name: String?,
        launchId: String
    ) {

        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        simpleDateFormat.timeZone = Calendar.getInstance().timeZone
        val launchTimeLocal = simpleDateFormat.format(Date(nextLaunchTime * 1000))

        val pendingIntent = tapActionBuilder(launchId)


        val title = "New Rocket Launch $name"
        val content =
            "In $launchTimeLocal minutes there is a new Launch.Flight Number: ${flightNumber.toString()}"

        val builder = NotificationCompat.Builder(ctx, USERS_LAUNCHES_WORKER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_rocketspacevehiclenverticalpositionsvgrepocom)
            .setContentTitle(title)
            .setContentIntent(pendingIntent)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager: NotificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(notificationManager)
        notificationManager.notify(USERS_LAUNCHES_NOTIFY_ID, builder.build())
    }

    private suspend fun getAllLaunchesFromDb(): List<Launch> {
        val currentTime = System.currentTimeMillis() / 1000

        return launchesDao.getLaunchesAfterNow(currentTime).sortedBy { it.dateUnix }
    }

    private fun getAllLaunchesToNotifyFromDb(): List<LaunchToNotify> {
        val currentTime = System.currentTimeMillis() / 1000

        return launchesDao.getAllLaunchToNotify()
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {

        val name = "NotificationUserLaunchesChannel"
        val descriptionText = "UsersLaunchesNotification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel =
            NotificationChannel(USERS_LAUNCHES_WORKER_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }


        notificationManager.createNotificationChannel(channel)
    }

    private fun tapActionBuilder(launchId: String): PendingIntent {
        val args = Bundle()
        args.putString("launchId", launchId)
        return NavDeepLinkBuilder(applicationContext)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.launchDetailsFragment)
            .setArguments(args)
            .createPendingIntent()
    }

    private fun usersNotifyLaunches(
        apiLaunches: List<Launch>,
        notifyLaunches: List<LaunchToNotify>
    ): List<Launch> {
        return apiLaunches.filter {
            notifyLaunches.map { launchToNotify -> launchToNotify.id }.contains(it.id)
        }
    }
}