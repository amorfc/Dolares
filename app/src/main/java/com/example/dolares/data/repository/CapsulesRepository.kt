package com.example.dolares.data.repository

import com.example.dolares.data.remote.SpacexApiService
import timber.log.Timber

class CapsulesRepository(private val spacexApiService: SpacexApiService) {


    suspend fun fetchCapsulesSaveToDb() {
        Timber.d("fetching capsules from remotre")
        val response = spacexApiService.getAllCapsules()
        if(response.isSuccessful){
            Timber.d("Fetched All Capsules Successfully ${response.body()}")
            response.body()?.let {
                TODO("Save Those Item To Db For Caching")
            }
        }else Timber.e("Unsuccessfully fetched data ${response.errorBody()}")
    }

}