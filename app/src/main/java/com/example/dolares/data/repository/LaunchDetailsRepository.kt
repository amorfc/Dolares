package com.example.dolares.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.dolares.data.local.dao.LaunchesDao
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.data.remote.SpacexApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LaunchDetailsRepository(
    val dao: LaunchesDao,
    sharedPreferences: SharedPreferences
) : BaseRepository(sharedPreferences) {


    fun getALaunch(launchId: String): Flow<Launch?> = dao.getSpecificLaunch(launchId)
}