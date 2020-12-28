package com.example.dolares.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dolares.data.local.dao.LaunchPadsDao
import com.example.dolares.data.remote.SpacexApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LaunchPadsRepository(
    private val spacexApiService: SpacexApiService,
    private val launchPadsDao: LaunchPadsDao
):BaseRepository() {

    private val TAG = "LaunchPads Repository"

    private val launchPadsLoadingStatus: MutableLiveData<Boolean> = MutableLiveData(false)
    private val launchPadsSnackBarMessage: MutableLiveData<String> = MutableLiveData()

    fun getAllLaunchPadsFlowFromDb() = launchPadsDao.getAllLaunchPadsFlow()

    fun getLaunchPadsLoadingStatus(): LiveData<Boolean> = launchPadsLoadingStatus
    fun getLaunchPadsSnackBarMessage(): MutableLiveData<String> = launchPadsSnackBarMessage

    suspend fun fetchLaunchPadsSaveToDb(){
        launchPadsLoadingStatus.postValue(true)
        withContext(Dispatchers.IO){
            val response = spacexApiService.getAllLaunchPads()
            if(response.isSuccessful){
                response.body()?.let {
                    launchPadsDao.replaceAllLaunchPads(it)
                }
            }else{
                launchPadsSnackBarMessage.postValue("Network Problem Occurred")
                Log.i(TAG,"Unexpected Network Issue")
            }
        }
        launchPadsLoadingStatus.postValue(false)
    }

}