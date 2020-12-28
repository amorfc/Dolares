package com.example.dolares.data.local.converters

import androidx.room.TypeConverter
import com.example.dolares.data.local.model.launch.LaunchSite
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LaunchSiteConverter {

    private val gson = Gson()
    private val type = object : TypeToken<LaunchSite>(){}.type

    @TypeConverter
    fun toJson(launchSite: LaunchSite?):String? = gson.toJson(launchSite,type)

    fun fromJson(data:String?):LaunchSite? = gson.fromJson(data,type)

}