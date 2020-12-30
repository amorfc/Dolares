package com.example.dolares.data.local.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dolares.data.local.converters.JsonArrayToStringConverter
import com.example.dolares.data.local.converters.LocationConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "launch_pad_table")
data class LaunchPad(
    @SerializedName("attempted_launches")
    @ColumnInfo(name = "attempted_launches")
    val attemptedLaunches: Int?,
    @SerializedName("details")
    @ColumnInfo(name = "details")
    val details: String?,
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "launchPad_id")
    override val _id: Long?,
    @SerializedName("location")
    @TypeConverters(LocationConverter::class)
    @ColumnInfo(name = "location")
    val location: Location?,
    @SerializedName("site_id")
    @ColumnInfo(name = "site_id")
    val siteId: String?,
    @SerializedName("site_name_long")
    @ColumnInfo(name = "site_name_long")
    val siteNameLong: String?,
    @SerializedName("status")
    @ColumnInfo(name = "status")
    val status: String?,
    @SerializedName("successful_launches")
    @ColumnInfo(name = "successful_launches")
    val successfulLaunches: Int?,
    @SerializedName("vehicles_launched")
    @TypeConverters(JsonArrayToStringConverter::class)
    @ColumnInfo(name = "vehicles_launched")
    val vehiclesLaunched: List<String?>?,
    @SerializedName("wikipedia")
    @ColumnInfo(name = "wikipedia")
    val wikipedia: String?
): BaseModel() {
    data class Location(
        @SerializedName("latitude")
        val latitude: Double?,
        @SerializedName("longitude")
        val longitude: Double?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("region")
        val region: String?
    )
}