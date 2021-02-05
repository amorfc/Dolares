package com.example.dolares.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.dolares.util.PREFS_KEY_REFRESH_INTERVAL

open class BaseRepository(
    private val sharedPrefs: SharedPreferences

) {
    open val TAG = "BaseRepository"

    fun checkIfDataRefreshNeeded(lastRefreshKey: String): Boolean {
        val currentTimeMillis: Long = System.currentTimeMillis()
        val lastRefreshTime = sharedPrefs.getLong(lastRefreshKey, 0)
        Log.d(TAG,"Last refresh time: $lastRefreshTime")
        // Get refresh interval set in app settings (in hours) and multiply to get value in ms
        val refreshIntervalHours =
            sharedPrefs.getString(PREFS_KEY_REFRESH_INTERVAL, "3")?.toInt() ?: 3
        val refreshIntervalMillis = refreshIntervalHours * NUMBER_OF_MILLISECONDS_IN_HOUR
        Log.d(TAG,"Refresh Interval: $refreshIntervalMillis")

        return currentTimeMillis - lastRefreshTime > refreshIntervalMillis
    }

    fun saveDataRefreshTime(refreshKey:String){
        with(sharedPrefs.edit()){
            putLong(refreshKey,System.currentTimeMillis())
            apply()
        }
    }


    companion object {
        const val NUMBER_OF_MILLISECONDS_IN_HOUR = 360000
    }
}