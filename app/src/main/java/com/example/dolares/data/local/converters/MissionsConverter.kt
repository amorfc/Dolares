package com.example.dolares.data.local.converters

import androidx.room.TypeConverter
import com.example.dolares.data.local.model.Capsule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class MissionsConverter {
    private val gson = Gson()

    @TypeConverter
    fun missionListToJson(missions: MutableList<Capsule.Mission>?): String?{
        return gson.toJson(missions)
    }

    @TypeConverter
    fun JsonToMissionList(data: String?):MutableList<Capsule.Mission>?{
        if(data == null){
            return Collections.emptyList()
        }

        val listType = object : TypeToken<MutableList<Capsule.Mission>>() {
        }.type

        return gson.fromJson(data,listType)
    }
}