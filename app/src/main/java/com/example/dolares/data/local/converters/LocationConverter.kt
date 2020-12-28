package com.example.dolares.data.local.converters

import androidx.room.TypeConverter
import com.example.dolares.data.local.model.LaunchPad
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocationConverter {
    private val gson = Gson()
    private val type = object : TypeToken<LaunchPad.Location>(){}.type

    @TypeConverter
    fun toJson(location:LaunchPad.Location?):String? = gson.toJson(location,type)

    @TypeConverter
    fun fromJson(data:String?):LaunchPad.Location? = gson.fromJson(data,type)

}