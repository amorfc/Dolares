package com.example.dolares.data.local.model.launch

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "launch_to_notify_table")
data class LaunchToNotify(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "isNotifyActive")
    val isNotifyActive:Boolean
    )