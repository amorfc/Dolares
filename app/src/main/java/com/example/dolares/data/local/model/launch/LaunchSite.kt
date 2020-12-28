package com.example.dolares.data.local.model.launch

import com.google.gson.annotations.SerializedName

data class LaunchSite(
    @SerializedName("site_id")
    val siteId: String?,
    @SerializedName("site_name")
    val siteName: String?,
    @SerializedName("site_name_long")
    val siteNameLong: String?
)
