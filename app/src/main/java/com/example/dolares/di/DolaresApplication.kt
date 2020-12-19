package com.example.dolares.di

import android.app.Application
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class DolaresApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@DolaresApplication)
            modules(listOf(remoteDataSourceModule))
        }

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}