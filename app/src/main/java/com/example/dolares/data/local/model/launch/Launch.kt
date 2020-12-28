package com.example.dolares.data.local.model.launch


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dolares.data.local.converters.JsonArrayToStringConverter
import com.example.dolares.data.local.converters.LaunchSiteConverter
import com.example.dolares.data.local.converters.LinksConverter
import com.example.dolares.data.local.converters.RocketConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "launch_table")
data class Launch(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val _id: Long,
    @SerializedName("details")
    @ColumnInfo(name = "details")
    val details: String?,
    @SerializedName("flight_number")
    @ColumnInfo(name = "flight_number")
    val flightNumber: Int?,
    @SerializedName("launch_date_unix")
    @ColumnInfo(name = "launch_date_unix")
    val launchDateUnix: Int?,
    @SerializedName("launch_site")
    @TypeConverters(LaunchSiteConverter::class)
    @ColumnInfo(name = "launch_site")
    val launchSite: LaunchSite?,
    @SerializedName("launch_success")
    @ColumnInfo(name = "launch_success")
    val launchSuccess: Boolean?,
    @SerializedName("launch_window")
    @ColumnInfo(name = "launch_window")
    val launchWindow: Int?,
    @SerializedName("launch_year")
    @ColumnInfo(name = "launch_year")
    val launchYear: String?,
    @SerializedName("links")
    @TypeConverters(LinksConverter::class)
    @ColumnInfo(name = "links")
    val links: Links?,
    @SerializedName("mission_id")
    @TypeConverters(JsonArrayToStringConverter::class)
    @ColumnInfo(name = "mission_id")
    val missionId: MutableList<String>?,
    @SerializedName("mission_name")
    @ColumnInfo(name = "mission_name")
    val missionName: String?,
    @SerializedName("rocket")
    @TypeConverters(RocketConverter::class)
    @ColumnInfo(name = "rocket")
    val rocket: Rocket?,
    @SerializedName("upcoming")
    @ColumnInfo(name = "upcoming")
    val upcoming: Boolean?
) {

    data class Links(
        @SerializedName("article_link")
        val articleLink: String?,
        @SerializedName("flickr_images")
        val flickrImages: List<Any?>?,
        @SerializedName("mission_patch")
        val missionPatch: String?,
        @SerializedName("mission_patch_small")
        val missionPatchSmall: String?,
        @SerializedName("presskit")
        val presskit: Any?,
        @SerializedName("reddit_campaign")
        val redditCampaign: Any?,
        @SerializedName("reddit_launch")
        val redditLaunch: Any?,
        @SerializedName("reddit_media")
        val redditMedia: Any?,
        @SerializedName("reddit_recovery")
        val redditRecovery: Any?,
        @SerializedName("video_link")
        val videoLink: String?,
        @SerializedName("wikipedia")
        val wikipedia: String?,
        @SerializedName("youtube_id")
        val youtubeId: String?
    )


}
