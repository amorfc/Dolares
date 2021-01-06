package com.example.dolares.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dolares.data.local.dao.LaunchPadsDao
import com.example.dolares.data.remote.SpacexApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LaunchPadsRepository(
    private val spacexApiService: SpacexApiService,
    private val launchPadsDao: LaunchPadsDao,
    sharedPreferences: SharedPreferences
):BaseRepository(sharedPreferences) {

    override val TAG = "LaunchPads Repository"

    private val launchPadsLoadingStatus: MutableLiveData<Boolean> = MutableLiveData(false)
    private val launchPadsSnackBarMessage: MutableLiveData<String> = MutableLiveData()

    fun getAllLaunchPadsFlowFromDb() = launchPadsDao.getAllLaunchPadsFlow()

    suspend fun fetchLaunchPadsSaveToDb(){
        withContext(Dispatchers.IO){
            val response = spacexApiService.getAllLaunchPads()
            Log.d(TAG,"Response  -> ${response.isSuccessful}")
            if(response.isSuccessful){
                response.body()?.let {
                    Log.d(TAG,"Fetched All Pads Successfully ${response.body()}")
                    launchPadsDao.replaceAllLaunchPads(it)
                }
            }
        }
    }

}