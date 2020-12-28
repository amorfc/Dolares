package com.example.dolares.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.dolares.data.local.dao.CoresDao
import com.example.dolares.data.local.model.Core
import com.example.dolares.data.remote.SpacexApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CoresRepository(
    private val spacexApiService: SpacexApiService,
    private val coresDao: CoresDao
) : BaseRepository() {
    val TAG = "CoresRepository"

    private val coresLoadingStatus: MutableLiveData<Boolean> = MutableLiveData(false)
    private val coresSnackBarMessage: MutableLiveData<String> = MutableLiveData()

    fun getAllCoresFlowFromDb(): Flow<List<Core>> = coresDao.getAllCoresFlow()

    suspend fun fetchAllDataSaveToDb() {
        coresLoadingStatus.postValue(true)
        withContext(Dispatchers.IO) {
            val response = spacexApiService.getAllCores()
            if (response.isSuccessful) {
                response.body()?.let {
                    coresDao.replaceAllCores(it)
                }
            } else {
                coresSnackBarMessage.postValue("Network problem occurred")
                Log.e(TAG, "Unsuccessfully fetched data ${response.errorBody()}")
            }
        }
        coresLoadingStatus.postValue(false)
    }

}