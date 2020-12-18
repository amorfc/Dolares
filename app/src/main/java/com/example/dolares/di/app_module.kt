package com.example.dolares.di

import com.example.dolares.data.remote.SpacexApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val retrofitModule = module {

    fun provideGson():Gson{
        return GsonBuilder().create()
    }

    fun provideHttpClient():OkHttpClient{
        val okHttpBuilder = OkHttpClient.Builder()

        return okHttpBuilder.build()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.spacexdata.com/v3/")
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    single {
        provideGson()
        provideHttpClient()
        provideRetrofit(get(), get())
    }

}