package com.example.dolares.di

import android.app.Application
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import com.example.dolares.workers.MyWorkerFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DolaresApplication : Application(), Configuration.Provider {

    override fun getWorkManagerConfiguration(): Configuration{
        val myWorkerFactory = DelegatingWorkerFactory()
        myWorkerFactory.addFactory(MyWorkerFactory())

        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(myWorkerFactory)
            .build()
    }
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()//def INFO
            androidContext(this@DolaresApplication)
            modules(listOf(
                appModule,
                remoteDataSourceModule,
                capsulesModule,
                coresModule,
                launchesModule,
                launchPadsModule,
                launchDetailsModule))
        }
    }
}