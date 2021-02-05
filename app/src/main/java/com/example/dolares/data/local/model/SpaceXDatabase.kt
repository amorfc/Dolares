package com.example.dolares.data.local.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dolares.data.local.converters.*
import com.example.dolares.data.local.dao.*
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.data.local.model.launch.LaunchToNotify

@Database(entities = [Capsule::class,Core::class,Launch::class,LaunchPad::class,LaunchToNotify::class],version = 9)

@TypeConverters(value=[
    LaunchesListToStringConverter::class,
    LinksToJsonConverter::class,
    ListOfStringToJsonConverter::class
])

abstract class SpaceXDatabase: RoomDatabase() {

    abstract fun capsulesDao(): CapsulesDao
    abstract fun coreDao(): CoresDao
    abstract fun launchesDao(): LaunchesDao
    abstract fun launchPadsDao(): LaunchPadsDao
}