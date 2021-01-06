package com.example.dolares.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dolares.data.local.dao.LaunchPadsDao
import com.example.dolares.data.local.dao.LaunchesDao
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.data.remote.SpacexApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.lang.Exception

class LaunchesRepository(
    private val spacexApiService: SpacexApiService,
    private val launchesDao: LaunchesDao
): BaseRepository() {

    private val launchesLoadingStatus: MutableLiveData<Boolean> = MutableLiveData(false)
    private val launchesSnackBar: MutableLiveData<String> = MutableLiveData()

    val TAG = "LaunchesRepository"

    fun getAllLaunchesFlowFromDb():Flow<List<Launch>> = launchesDao.getAllLaunchesFlow()

    fun getLaunchesLoadingStatus():LiveData<Boolean> = launchesLoadingStatus
    fun getLaunchesSnackBarMessage():MutableLiveData<String> = launchesSnackBar

    suspend fun fetchAllLaunchesSaveToDb(){
        launchesLoadingStatus.postValue(true)
        withContext(Dispatchers.IO){
            val response = spacexApiService.getAllLaunches()
            Log.d(TAG,"Response  -> ${response.isSuccessful}")
            if(response.isSuccessful){
                    response.body()?.let {
                        Log.d(TAG,"Fetched All Launches Successfully ${response.body()}")
                        launchesDao.replaceAllLaunches(it)
                    }
                }else {
                    Log.i(TAG, "Unexpected Problem")
                    launchesSnackBar.postValue("Network Problem Occurred")
                }
        }
        launchesLoadingStatus.postValue(false)
    }
}