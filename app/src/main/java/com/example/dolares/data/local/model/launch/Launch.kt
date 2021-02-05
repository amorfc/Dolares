package com.example.dolares.data.local.model.launch


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dolares.data.local.converters.LinksToJsonConverter
import com.example.dolares.data.local.converters.ListOfStringToJsonConverter
import com.example.dolares.data.local.model.BaseModel
import com.google.gson.annotations.SerializedName


@Entity(tableName = "launches_table")
data class Launch(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: String,
    @ColumnInfo(name = "auto_update")
    @SerializedName("auto_update")
    val autoUpdate: Boolean?,
    @TypeConverters(ListOfStringToJsonConverter::class)
    @ColumnInfo(name = "capsules")
    @SerializedName("capsules")
    val capsules: List<String?>?,
//    @ColumnInfo(name = "cores")
//    @SerializedName("cores")
//    val cores: List<Core?>?,
//    @ColumnInfo(name = "crew")
//    @SerializedName("crew")
//    val crew: List<Any?>?,
    @ColumnInfo(name = "date_local")
    @SerializedName("date_local")
    val dateLocal: String?,
    @ColumnInfo(name = "date_precision")
    @SerializedName("date_precision")
    val datePrecision: String?,
    @ColumnInfo(name = "date_unix")
    @SerializedName("date_unix")
    var dateUnix: Long?,
    @ColumnInfo(name = "date_utc")
    @SerializedName("date_utc")
    val dateUtc: String?,
    @ColumnInfo(name = "details")
    @SerializedName("details")
    val details: String?,
//    @ColumnInfo(name = "failures")
//    @SerializedName("failures")
//    val failures: List<Any?>?,
//    @ColumnInfo(name = "fairings")
//    @SerializedName("fairings")
//    val fairings: Any?,
    @ColumnInfo(name = "flight_number")
    @SerializedName("flight_number")
    val flightNumber: Int?,
    @ColumnInfo(name = "launchpad")
    @SerializedName("launchpad")
    val launchpad: String?,
    @TypeConverters(LinksToJsonConverter::class)
    @ColumnInfo(name = "links")
    @SerializedName("links")
    val links: Links?,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String?,
    @ColumnInfo(name = "net")
    @SerializedName("net")
    val net: Boolean?,
    @TypeConverters(ListOfStringToJsonConverter::class)
    @ColumnInfo(name = "payloads")
    @SerializedName("payloads")
    val payloads: List<String?>?,
    @ColumnInfo(name = "rocket")
    @SerializedName("rocket")
    val rocket: String?,
//    @ColumnInfo(name = "ships")
//    @SerializedName("ships")
//    val ships: List<Any?>?,
    @ColumnInfo(name = "static_fire_date_unix")
    @SerializedName("static_fire_date_unix")
    val staticFireDateUnix: Int?,
    @ColumnInfo(name = "static_fire_date_utc")
    @SerializedName("static_fire_date_utc")
    val staticFireDateUtc: String?,
    @ColumnInfo(name = "success")
    @SerializedName("success")
    val success: Boolean?,
    @ColumnInfo(name = "tdb")
    @SerializedName("tdb")
    val tdb: Boolean?,
    @ColumnInfo(name = "upcoming")
    @SerializedName("upcoming")
    val upcoming: Boolean?,
    @ColumnInfo(name = "window")
    @SerializedName("window")
    val window: Int?
) {
    data class Core(
        @SerializedName("core")
        val core: String?,
        @SerializedName("flight")
        val flight: Int?,
        @SerializedName("gridfins")
        val gridfins: Boolean?,
        @SerializedName("landing_attempt")
        val landingAttempt: Boolean?,
        @SerializedName("landing_success")
        val landingSuccess: Boolean?,
        @SerializedName("landing_type")
        val landingType: String?,
        @SerializedName("landpad")
        val landpad: String?,
        @SerializedName("legs")
        val legs: Boolean?,
        @SerializedName("reused")
        val reused: Boolean?
    )

    data class Links(
        @SerializedName("article")
        val article: String?,
        @SerializedName("flickr")
        val flickr: Flickr?,
        @SerializedName("patch")
        val patch: Patch?,
        @SerializedName("presskit")
        val presskit: String?,
        @SerializedName("reddit")
        val reddit: Reddit?,
        @SerializedName("webcast")
        val webcast: String?,
        @SerializedName("wikipedia")
        val wikipedia: String?,
        @SerializedName("youtube_id")
        val youtubeId: String?
    ) {
        data class Flickr(
            @SerializedName("original")
            val original: List<String?>?,
            @SerializedName("small")
            val small: List<Any?>?
        )

        data class Patch(
            @SerializedName("large")
            val large: String?,
            @SerializedName("small")
            val small: String?
        )

        data class Reddit(
            @SerializedName("campaign")
            val campaign: String?,
            @SerializedName("launch")
            val launch: String?,
            @SerializedName("media")
            val media: String?,
            @SerializedName("recovery")
            val recovery: Any?
        )
    }
}