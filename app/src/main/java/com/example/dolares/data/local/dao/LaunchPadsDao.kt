package com.example.dolares.data.local.dao

import androidx.room.*
import com.example.dolares.data.local.model.LaunchPad
import com.example.dolares.data.local.model.launch.Launch
import kotlinx.coroutines.flow.Flow

@Dao
interface LaunchPadsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLaunchPads(launchPads:List<LaunchPad>)

    @Query("Select * From launch_pad_table")
    fun getAllLaunchPadsFlow(): Flow<List<LaunchPad>>

    @Transaction
    suspend fun replaceAllLaunchPads(launchPads: List<LaunchPad>){
        deleteAllLaunchPads()
        insertAllLaunchPads(launchPads)
    }

    @Query("Delete From launch_pad_table")
    suspend fun deleteAllLaunchPads()

}