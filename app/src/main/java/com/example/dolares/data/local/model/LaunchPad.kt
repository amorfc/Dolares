package com.example.dolares.data.local.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dolares.data.local.converters.LaunchesListToStringConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "launch_pads_table")
data class LaunchPad(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: String,
    @ColumnInfo(name = "full_name")
    @SerializedName("full_name")
    val fullName: String?,
    @ColumnInfo(name = "latitude")
    @SerializedName("latitude")
    val latitude: Double?,
    @ColumnInfo(name = "launch_attempts")
    @SerializedName("launch_attempts")
    val launchAttempts: Int?,
    @ColumnInfo(name = "launch_successes")
    @SerializedName("launch_successes")
    val launchSuccesses: Int?,
    @TypeConverters(LaunchesListToStringConverter::class)
    @ColumnInfo(name = "launches")
    @SerializedName("launches")
    val launches: List<String?>?,
    @ColumnInfo(name = "locality")
    @SerializedName("locality")
    val locality: String?,
    @ColumnInfo(name = "longitude")
    @SerializedName("longitude")
    val longitude: Double?,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?,
    @ColumnInfo(name = "region")
    @SerializedName("region")
    val region: String?,

    @ColumnInfo(name = "rockets")
    @SerializedName("rockets")
    val rockets: List<String?>?,
    @ColumnInfo(name = "status")
    @SerializedName("status")
    val status: String?,
    @ColumnInfo(name = "timezone")
    @SerializedName("timezone")
    val timezone: String?
)