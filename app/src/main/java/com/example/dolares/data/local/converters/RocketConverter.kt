package com.example.dolares.data.local.converters

import androidx.room.TypeConverter
import com.example.dolares.data.local.model.launch.Rocket
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RocketConverter {

    private val gson = Gson()
    private val type = object : TypeToken<Rocket>(){}.type

    @TypeConverter
    fun rocketToJson(rocket: Rocket?):String? = gson.toJson(rocket,type)

    @TypeConverter
    fun rocketFromJson(jsonRocket:String?):Rocket? = gson.fromJson(jsonRocket,type)

}