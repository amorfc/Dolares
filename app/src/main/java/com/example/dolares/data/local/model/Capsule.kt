package com.example.dolares.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dolares.data.local.converters.MissionsConverter
import com.google.gson.annotations.SerializedName

// Single capsule
@Entity(tableName = "capsules_table")
data class Capsule(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    override val _id: Long,
    @SerializedName("capsule_serial")
    @ColumnInfo(name = "capsule_serial")
    val capsuleSerial: String,
    @SerializedName("capsule_id")
    @ColumnInfo(name = "capsule_id")
    val capsuleId: String,
    @SerializedName("status")
    @ColumnInfo(name = "status")
    val status: String,
    @SerializedName("original_launch")
    @ColumnInfo(name = "original_launch")
    val originalLaunch: String?,
    @SerializedName("original_launch_unix")
    @ColumnInfo(name = "original_launch_unix")
    val originalLaunchUnix: Long?,
    @SerializedName("missions")
    @TypeConverters(MissionsConverter::class)
    @ColumnInfo(name = "missions")
    val missions: MutableList<Capsule.Mission>?,
    @SerializedName("landings")
    @ColumnInfo(name = "landings")
    val landings: Int,
    @SerializedName("type")
    @ColumnInfo(name = "type")
    val type: String,
    @SerializedName("details")
    @ColumnInfo(name = "details")
    val details: String?,
    @SerializedName("reuse_count")
    @ColumnInfo(name = "reuse_count")
    val reuseCount: Int
): BaseModel(){
    // Model for representation of Capsule missions (JSON array)
    data class Mission(
        @SerializedName("name")
        val name: String,
        @SerializedName("flight")
        val flight: Int
    )
}