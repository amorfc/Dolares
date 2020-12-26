package com.example.dolares.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.dolares.data.local.dao.CapsulesDao
import com.example.dolares.data.local.model.Capsule
import com.example.dolares.data.remote.SpacexApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

const val TAG = "CapsulesRepository"

class CapsulesRepository(
    private val spacexApiService: SpacexApiService,
    private val capsulesDao: CapsulesDao
    ) {


    fun getAllCapsulesFlow():Flow<List<Capsule>> = capsulesDao.getAllCapsulesFlow()

    suspend fun fetchCapsulesSaveToDb() {
        Log.d(TAG,"fetching capsules from remote")
        val response = spacexApiService.getAllCapsules()
        Log.d(TAG,"${response.isSuccessful}")
        if(response.isSuccessful){
            Log.d(TAG,"Fetched All Capsules Successfully ${response.body()}")
            response.body()?.let { listOfCapsules ->
                 capsulesDao.replaceCapsulesData(listOfCapsules)
            }
        }else Log.e(TAG,"Unsuccessfully fetched data ${response.errorBody()}")
    }

}