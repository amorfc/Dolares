package com.example.dolares.data.local.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dolares.data.local.converters.MissionsConverter
import com.example.dolares.data.local.dao.CapsulesDao
import com.example.dolares.data.local.dao.CoresDao

@Database(entities = [Capsule::class,Core::class],version = 1)

@TypeConverters(value=[MissionsConverter::class])

abstract class SpaceXDatabase: RoomDatabase() {

    abstract fun capsulesDao(): CapsulesDao
    abstract fun coreDao(): CoresDao

}