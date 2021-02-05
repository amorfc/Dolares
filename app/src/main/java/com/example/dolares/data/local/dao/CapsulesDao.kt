package com.example.dolares.data.local.dao

import androidx.room.*
import com.example.dolares.data.local.model.Capsule
import kotlinx.coroutines.flow.Flow

@Dao
interface CapsulesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCapsules(capsules:List<Capsule>)

    @Transaction
    suspend fun replaceCapsulesData(capsules: List<Capsule>){
        deleteALlCapsules()
        insertCapsules(capsules = capsules)
    }

    @Query("Select * From capsules_table")
    fun getAllCapsulesFlow():Flow<List<Capsule>>

    @Query("Select COUNT(id) From capsules_table")
    fun getNumberOfCapsulesFlow():Flow<Int>

    @Query("Delete From capsules_table" )
    suspend fun deleteALlCapsules()
}