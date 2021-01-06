package com.example.dolares.data.local.converters

import androidx.room.TypeConverter
import com.example.dolares.data.local.model.launch.Launch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LinksToJsonConverter {
    val gson = Gson()
    val type = object : TypeToken<Launch.Links>(){}.type


    @TypeConverter
    fun linksToJson(links:Launch.Links?):String? = gson.toJson(links,type)

    @TypeConverter
    fun jsonToLinks(string: String?): Launch.Links? = gson.fromJson(string,type)
}