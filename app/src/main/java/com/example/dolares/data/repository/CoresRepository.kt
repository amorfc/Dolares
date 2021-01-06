package com.example.dolares.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.dolares.data.local.dao.CoresDao
import com.example.dolares.data.local.model.Core
import com.example.dolares.data.remote.SpacexApiService
import com.example.dolares.util.CAPSULE_DATA_REFRESH_KEY
import com.example.dolares.util.CORE_DATA_REFRESH_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception

class CoresRepository(
    private val spacexApiService: SpacexApiService,
    private val coresDao: CoresDao,
    sharedPreferences: SharedPreferences
) : BaseRepository(sharedPreferences) {
    override val TAG = "CoresRepository"
    private val REFRESH_KEY = CORE_DATA_REFRESH_KEY


    private var coresDataStatus: MutableLiveData<Result<Any>> = MutableLiveData<Result<Any>>()

    fun getAllCoresFlowFromDb(): Flow<List<Core>> = coresDao.getAllCoresFlow()

    suspend fun executeRefreshData(){
        coresDataStatus.value = Result.Loading(true)
        withContext(Dispatchers.IO){
            try {
                fetchAllDataSaveToDb()
            }catch (e: Exception){
                when(e){
                    is IOException -> coresDataStatus.postValue(Result.Error(exception = e))
                    else -> {
                        coresDataStatus.postValue(Result.Error(message = "Unexpected Problem Occurred"))
                        Log.i(TAG,"Data couldn't refresh ${e.message}")
                    }
                }
            }
            coresDataStatus.value = Result.Loading(false)
        }
    }

    suspend fun refreshData(refresh:Boolean = false){
        if(!refresh){
            val isNeeded = withContext(Dispatchers.IO){
                checkIfDataRefreshNeeded(REFRESH_KEY)
            }
            if(!isNeeded){
                Log.i(TAG,"Dont needed refresh")
                return
            }
        }
        executeRefreshData()
    }

    suspend fun fetchAllDataSaveToDb() {
        withContext(Dispatchers.IO) {
            val response = spacexApiService.getAllCores()
            Log.d(TAG,"Response  -> ${response.isSuccessful}")
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d(TAG,"Fetched All Cores Successfully ${response.body()}")
                    coresDao.replaceAllCores(it)
                }
                saveDataRefreshTime(REFRESH_KEY)
            }
        }
    }

}