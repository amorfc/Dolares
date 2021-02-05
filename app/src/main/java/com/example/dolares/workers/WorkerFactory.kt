package com.example.dolares.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters

class MyWorkerFactory : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when(workerClassName){
            NotificationLaunchesWorker::class.java.name -> NotificationLaunchesWorker(appContext, workerParameters)
            else -> null
        }

    }
}