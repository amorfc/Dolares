package com.example.dolares.data.local.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dolares.data.local.dao.CapsulesDao

@Database(entities = [Capsule::class],version = 1)

abstract class SpaceXDatabase: RoomDatabase() {

    abstract fun capsulesDao(): CapsulesDao

}