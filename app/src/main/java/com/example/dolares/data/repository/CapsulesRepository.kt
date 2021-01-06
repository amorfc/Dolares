package com.example.dolares.data.repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.dolares.data.local.dao.CapsulesDao
import com.example.dolares.data.local.model.Capsule
import com.example.dolares.data.remote.SpacexApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception


class CapsulesRepository(
    private val spacexApiService: SpacexApiService,
    private val capsulesDao: CapsulesDao
): BaseRepository(){

    private val TAG = "CapsulesRepository"

    private var capsulesDataStatus: MutableLiveData<Result<Any>> = MutableLiveData<Result<Any>>()

    fun getAllCapsulesFlow():Flow<List<Capsule>> = capsulesDao.getAllCapsulesFlow()


    suspend fun executeRefreshData(){
        capsulesDataStatus.value = Result.Loading(true)
        withContext(Dispatchers.IO){
            try {
                fetchAllCapsulesAndSaveToDb()
            }catch (e:Exception){
                when(e){
                    is IOException -> capsulesDataStatus.postValue(Result.Error(exception = e))
                    else -> {
                        capsulesDataStatus.postValue(Result.Error(message = "Unexpected Problem Occurred"))
                        Log.i(TAG,"Data couldn't refresh ${e.message}")
                    }
                }
            }
            capsulesDataStatus.value = Result.Loading(false)
        }
    }

    private suspend fun fetchAllCapsulesAndSaveToDb(){
        Log.d(TAG,"fetching capsules from remote")
        val response = spacexApiService.getAllCapsules()
        Log.d(TAG,"Response  -> ${response.isSuccessful}")
        if(response.isSuccessful){
            Log.d(TAG,"Fetched All Capsules Successfully ${response.body()}")
            response.body()?.let { listOfCapsules ->
                capsulesDao.insertCapsules(listOfCapsules)
            }
        }else Log.e(TAG,"Unsuccessfully fetched data ${response.errorBody()}")
    }
    
}