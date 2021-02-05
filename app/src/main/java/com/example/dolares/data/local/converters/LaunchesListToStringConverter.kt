package com.example.dolares.data.local.converters

import androidx.room.TypeConverter
import com.example.dolares.data.local.model.launch.Launch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LaunchesListToStringConverter {

    val gson = Gson()
    val type = object : TypeToken<List<Launch>>(){}.type

    @TypeConverter
    fun launchesListToJson(launches:List<Launch>?):String? = gson.toJson(launches,type)

    @TypeConverter
    fun jsonToLaunchesList(string: String?):List<Launch> = gson.fromJson(string,type)

}