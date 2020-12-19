package com.example.dolares.data.remote

import com.example.dolares.data.local.model.Capsule
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface SpacexApiService {

    @GET("capsules")
    suspend fun getAllCapsules(): Response<List<Capsule>>

}