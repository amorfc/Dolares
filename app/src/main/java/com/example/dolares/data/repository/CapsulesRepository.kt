package com.example.dolares.data.repository

import com.example.dolares.data.local.dao.CapsulesDao
import com.example.dolares.data.remote.SpacexApiService
import timber.log.Timber

class CapsulesRepository(
    private val spacexApiService: SpacexApiService,
    private val capsulesDao: CapsulesDao
    ) {


    suspend fun fetchCapsulesSaveToDb() {
        Timber.d("fetching capsules from remote")
        val response = spacexApiService.getAllCapsules()
        if(response.isSuccessful){
            Timber.d("Fetched All Capsules Successfully ${response.body()}")
            response.body()?.let { listOfCapsules ->
                 capsulesDao.replaceCapsulesData(listOfCapsules)
            }
        }else Timber.e("Unsuccessfully fetched data ${response.errorBody()}")
    }

}