package com.example.dolares.data.local.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dolares.data.local.converters.LaunchesListToStringConverter
import com.example.dolares.data.local.converters.ListOfStringToJsonConverter
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cores_table")
data class Core(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: String,
    @SerializedName("asds_attempts")
    @ColumnInfo(name = "asds_attempts")
    val asdsAttempts: Int?,
    @SerializedName("asds_landings")
    @ColumnInfo(name = "asds_landings")
    val asdsLandings: Int?,
    @SerializedName("block")
    @ColumnInfo(name = "block")
    val block: Int?,
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
    @SerializedName("rtls_attempts")
    @ColumnInfo(name = "rtls_attempts")
    val rtlsAttempts: Int?,
    @SerializedName("rtls_landings")
    @ColumnInfo(name = "rtls_landings")
    val rtlsLandings: Int?,
    @SerializedName("serial")
    @ColumnInfo(name = "serial")
    val serial: String?,
    @SerializedName("status")
    @ColumnInfo(name = "status")
    val status: String?
):Parcelable