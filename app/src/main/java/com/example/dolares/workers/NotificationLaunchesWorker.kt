package com.example.dolares.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.dolares.data.local.dao.LaunchesDao
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.util.NOTIFICATION_WORKING_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class NotificationLaunchesWorker(ctx:Context,params: WorkerParameters) :CoroutineWorker(ctx,params),KoinComponent{

    private val launchesDao: LaunchesDao by inject()

    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            var launches:List<Launch>  = getAllLaunchesFromDb()
            if (launches.isNullOrEmpty()) Result.retry()

            val firstUpcomingLaunch: Launch = launches[0]
            val nextLaunchTime: Long = firstUpcomingLaunch.dateUnix ?: 0L

            if (nextLaunchTime == 0L || firstUpcomingLaunch.name == null || firstUpcomingLaunch.name.isBlank()) Result.retry()

            val currentTime = System.currentTimeMillis() / 1000
            if (nextLaunchTime - currentTime < 86400) {
                fireNotification(firstUpcomingLaunch.flightNumber, nextLaunchTime, firstUpcomingLaunch.name)
            }
                Result.success()
        }

    private fun fireNotification(flightNumber: Int?, nextLaunchTime: Long, name: String?) {

    }

    suspend fun getAllLaunchesFromDb():List<Launch>{
        val currentTime = System.currentTimeMillis() / 1000

        return launchesDao.getLaunchesAfterNow(currentTime).sortedBy { it.dateUnix }
    }
}