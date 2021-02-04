package com.example.dolares.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.dolares.R
import com.example.dolares.util.LAUNCHES_NOTIFICATIONS_KEY
import com.example.dolares.util.NOTIFICATION_WORKING_TAG
import com.example.dolares.workers.NotificationLaunchesWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPrefsListener: SharedPreferences.OnSharedPreferenceChangeListener
    private val preferences: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navController = findNavController(R.id.nav_host_fragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        findViewById<BottomNavigationView>(R.id.bottom_navigation)
            .setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    bottomNavigationView.visibility = View.GONE
                }
                R.id.registerFragment -> {
                    bottomNavigationView.visibility = View.GONE
                }
                R.id.coresFragment -> {
                }
                R.id.capsulesFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.launchesFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }

        sharedPrefsListener = SharedPreferences.OnSharedPreferenceChangeListener { _, prefKey ->
            when(prefKey){
                LAUNCHES_NOTIFICATIONS_KEY->{
                    handleNotificationPref()
                }
            }
        }

    }
    private fun handleNotificationPref(){
        val isNotificationWanted = preferences.getBoolean(
            LAUNCHES_NOTIFICATIONS_KEY,
            false
        )
        if(isNotificationWanted){
            setLaunchesNotificationsWork()
        }else{
            deleteLaunchesNotificationsWork()
        }
    }

    private fun deleteLaunchesNotificationsWork() {
            WorkManager.getInstance(this).cancelUniqueWork(NOTIFICATION_WORKING_TAG)
    }

    private fun setLaunchesNotificationsWork() {
        val notificationWorker = PeriodicWorkRequestBuilder<NotificationLaunchesWorker>(1,TimeUnit.MINUTES)
            .addTag(NOTIFICATION_WORKING_TAG)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            NOTIFICATION_WORKING_TAG,
            ExistingPeriodicWorkPolicy.REPLACE,
            notificationWorker
        )

    }

    override fun onStart() {
        super.onStart()
        preferences.registerOnSharedPreferenceChangeListener(sharedPrefsListener)
    }

    override fun onStop() {
        super.onStop()
        preferences.unregisterOnSharedPreferenceChangeListener(sharedPrefsListener)
    }
}