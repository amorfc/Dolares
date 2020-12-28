package com.example.dolares.data.local.dao

import androidx.room.*
import com.example.dolares.data.local.model.Core
import kotlinx.coroutines.flow.Flow


@Dao
interface CoresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCores(cores:List<Core>)

    @Query("Select * From core_table")
    fun getAllCoresFlow(): Flow<List<Core>>

    @Query("Delete From core_table")
    suspend fun deleteAllCore()

    @Transaction
    suspend fun replaceAllCores(cores: List<Core>){
        deleteAllCore()
        insertAllCores(cores = cores)
    }

}