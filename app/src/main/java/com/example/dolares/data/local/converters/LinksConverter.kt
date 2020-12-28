package com.example.dolares.data.local.converters

import androidx.room.TypeConverter
import com.example.dolares.data.local.model.launch.Launch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class LinksConverter {
    private val gson = Gson()
    private val type = object : TypeToken<Launch.Links>(){

    }.type
    @TypeConverter
    fun linksToJson(links: Launch.Links?): String?{
        return gson.toJson(links,type)
    }

    @TypeConverter
    fun jsonToLinks(linksString: String?):Launch.Links?{
        if(linksString == null){
            return null
        }
        return gson.fromJson(linksString,type)
    }

}