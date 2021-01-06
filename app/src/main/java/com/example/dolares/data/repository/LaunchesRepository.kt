package com.example.dolares.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.dolares.data.local.dao.LaunchesDao
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.data.remote.SpacexApiService
import com.example.dolares.util.LAUNCH_DATA_REFRESH_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.IOException

class LaunchesRepository(
    private val spacexApiService: SpacexApiService,
    private val launchesDao: LaunchesDao,
    sharedPreferences: SharedPreferences
) : BaseRepository(sharedPreferences) {


    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData(false)
    val snackBarMessage: MutableLiveData<String> = MutableLiveData()


    override val TAG = "LaunchesRepository"
    private val REFRESH_KEY = LAUNCH_DATA_REFRESH_KEY

    fun getAllLaunchesFlowFromDb(): Flow<List<Launch>> = launchesDao.getAllLaunchesFlow()

    suspend fun executeRefreshData() {
        loadingStatus.value = true
        withContext(Dispatchers.IO) {
            try {
                fetchAllLaunchesSaveToDb()
            } catch (e: Exception) {
                when (e) {
                    is IOException -> snackBarMessage.postValue(e.message)
                    else -> {
                        snackBarMessage.postValue("Unexpected Problem Occurred")
                        Log.i(TAG, "Data couldn't refresh ${e.message}")
                    }
                }
            }
            loadingStatus.postValue(false)
        }
    }

    suspend fun refreshData(refresh: Boolean = false) {
        if (!refresh) {
            val isNeeded = withContext(Dispatchers.IO) {
                checkIfDataRefreshNeeded(REFRESH_KEY)
            }
            if (!isNeeded) {
                Log.i(TAG, "Dont needed refresh")
                return
            }
        }
        executeRefreshData()
    }


    suspend fun fetchAllLaunchesSaveToDb() {
        withContext(Dispatchers.IO) {
            val response = spacexApiService.getAllLaunches()
            Log.d(TAG, "Response  -> ${response.isSuccessful}")
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d(TAG, "Fetched All Launches Successfully ${response.body()}")
                    launchesDao.replaceAllLaunches(it)
                }
                saveDataRefreshTime(REFRESH_KEY)
            }
        }
    }
}