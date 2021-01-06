package com.example.dolares.data.local.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dolares.data.local.converters.LaunchesListToStringConverter
import com.example.dolares.data.local.converters.ListOfStringToJsonConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "capsules_table")
data class Capsule(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    override val id: String,
    @SerializedName("land_landings")
    @ColumnInfo(name = "land_landings")
    val landLandings: Int?,
    @SerializedName("last_update")
    @ColumnInfo(name = "last_update")
    val lastUpdate: String?,
    @TypeConverters(ListOfStringToJsonConverter::class)
    @SerializedName("launches")
    @ColumnInfo(name = "launches")
    val launches: List<String?>?,
    @SerializedName("reuse_count")
    @ColumnInfo(name = "reuse_count")
    val reuseCount: Int?,
    @SerializedName("serial")
    @ColumnInfo(name = "serial")
    val serial: String?,
    @SerializedName("status")
    @ColumnInfo(name = "status")
    val status: String?,
    @SerializedName("type")
    @ColumnInfo(name = "type")
    val type: String?,
    @SerializedName("water_landings")
    @ColumnInfo(name = "water_landings")
    val waterLandings: Int?
):BaseModel()