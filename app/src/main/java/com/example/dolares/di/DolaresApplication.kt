package com.example.dolares.di

import android.app.Application
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DolaresApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()//def INFO
            androidContext(this@DolaresApplication)
            modules(listOf(appModule, remoteDataSourceModule, capsulesModule))
        }
    }
}