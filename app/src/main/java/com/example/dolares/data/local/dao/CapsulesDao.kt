package com.example.dolares.data.local.dao

import androidx.room.*
import com.example.dolares.data.local.model.Capsule

@Dao
interface CapsulesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCapsules(capsules:List<Capsule>)

    @Transaction
    suspend fun replaceCapsulesData(capsules: List<Capsule>){
        deleteALlCapsules()
        insertCapsules(capsules = capsules)
    }

    @Query("Delete From capsules_table" )
    suspend fun deleteALlCapsules()
}