package com.example.dolares.data.remote

import com.example.dolares.data.local.model.Capsule
import com.example.dolares.data.local.model.Core
import com.example.dolares.data.local.model.LaunchPad
import com.example.dolares.data.local.model.launch.Launch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SpacexApiService {

    @GET("capsules")
    suspend fun getAllCapsules(): Response<List<Capsule>>

    @GET("cores")
    suspend fun getAllCores(): Response<List<Core>>

    @GET("launches")
    suspend fun getAllLaunches(): Response<List<Launch>>

    @GET("launchpads")
    suspend fun getAllLaunchPads(): Response<List<LaunchPad>>

//    @GET("launches")
//    suspend fun getSpecificLaunch(@Query("id")launchId:String):Response<Launch>

}