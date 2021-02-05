package com.example.dolares.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Collections

class ListOfStringToJsonConverter {

    private val gson = Gson()

    @TypeConverter
    fun jsonArrayToString(missionIds: List<String>?): String? {
        return gson.toJson(missionIds)
    }

    @TypeConverter
    fun stringToJsonArray(data: String?): List<String>? {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<String>>() {
        }.type

        return gson.fromJson(data, listType)
    }
}
