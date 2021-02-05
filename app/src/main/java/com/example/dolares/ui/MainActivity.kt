package com.example.dolares.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.dolares.R
import com.example.dolares.util.DAILY_LAUNCHES_NOTIFICATION_WORKING_TAG
import com.example.dolares.util.LAUNCHES_NOTIFICATIONS_KEY
import com.example.dolares.util.USERS_LAUNCHES_NOTIFICATION_WORKING_TAG
import com.example.dolares.workers.NotificationDailyLaunchesWorker
import com.example.dolares.workers.NotificationUsersSelectedLaunchesWorker
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
            setUsersLaunchesNotificationsWork()
            setDailyLaunchesNotificationsWork()
        }else{
            deleteUsersLaunchesNotificationsWork()
            deleteDailyLaunchesNotificationsWork()
        }
    }

    private fun deleteUsersLaunchesNotificationsWork() {
            WorkManager.getInstance(this).cancelUniqueWork(USERS_LAUNCHES_NOTIFICATION_WORKING_TAG)
    }

    private fun setUsersLaunchesNotificationsWork() {
        val notificationWorker = PeriodicWorkRequestBuilder<NotificationUsersSelectedLaunchesWorker>(1,TimeUnit.MINUTES)
            .addTag(USERS_LAUNCHES_NOTIFICATION_WORKING_TAG)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            USERS_LAUNCHES_NOTIFICATION_WORKING_TAG,
            ExistingPeriodicWorkPolicy.REPLACE,
            notificationWorker
        )

    }

    private fun deleteDailyLaunchesNotificationsWork() {
        WorkManager.getInstance(this).cancelUniqueWork(DAILY_LAUNCHES_NOTIFICATION_WORKING_TAG)
    }

    private fun setDailyLaunchesNotificationsWork() {
        val notificationWorker = PeriodicWorkRequestBuilder<NotificationDailyLaunchesWorker>(1,TimeUnit.DAYS)
            .addTag(DAILY_LAUNCHES_NOTIFICATION_WORKING_TAG)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            DAILY_LAUNCHES_NOTIFICATION_WORKING_TAG,
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