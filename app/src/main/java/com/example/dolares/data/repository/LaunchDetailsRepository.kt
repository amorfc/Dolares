package com.example.dolares.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.dolares.data.local.dao.LaunchesDao
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.data.local.model.launch.LaunchToNotify
import com.example.dolares.data.remote.SpacexApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class LaunchDetailsRepository(
    val dao: LaunchesDao,
    sharedPreferences: SharedPreferences
) : BaseRepository(sharedPreferences) {


    fun getALaunch(launchId: String): Flow<Launch?> = dao.getSpecificLaunch(launchId)

    suspend fun setLaunchNotifyStatus(launchId: String,isNotifyActive:Boolean) = withContext(Dispatchers.IO){
        val launchToNotify = LaunchToNotify(launchId,isNotifyActive)
        dao.insertNotifyLaunch(launchToNotify)
    }

    suspend fun getIfNotifyLaunchExist(launchId: String):LaunchToNotify? = withContext(Dispatchers.IO){

        val launchToNotify:LaunchToNotify? = dao.getALaunchToNotify(launchId)

        launchToNotify?.let {
            return@withContext launchToNotify
        }

        return@withContext null
    }
}