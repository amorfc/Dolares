package com.example.dolares.data.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dolares.data.local.dao.CapsulesDao
import com.example.dolares.data.local.model.Capsule
import com.example.dolares.data.remote.SpacexApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception

const val TAG = "CapsulesRepository"

class CapsulesRepository(
    private val spacexApiService: SpacexApiService,
    private val capsulesDao: CapsulesDao
): BaseRepository(){

    private var capsulesDataLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    private var capsulesSnackBar: MutableLiveData<String> = MutableLiveData()

    fun getAllCapsulesFlow():Flow<List<Capsule>> = capsulesDao.getAllCapsulesFlow()

    fun getDataLoadingStatus(): LiveData<Boolean> = capsulesDataLoading
    fun getCapsulesSnackBarMessage(): LiveData<String> = capsulesSnackBar

    suspend fun executeRefreshData(){
        capsulesDataLoading.value = true
        withContext(Dispatchers.IO){
            try {
                fetchCapsulesSaveToDb()
            }catch (e:Exception){
                when(e){
                    is IOException -> capsulesSnackBar.postValue("Network Problem Occurred")
                    else -> {
                        capsulesSnackBar.postValue("Unexpected Problem Occurred")
                        Log.i(TAG,"Data couldn't refresh ${e.message}")
                    }
                }
            }
            capsulesDataLoading.postValue(false)
        }
    }

    private suspend fun fetchCapsulesSaveToDb() {
        Log.d(TAG,"fetching capsules from remote")
        val response = spacexApiService.getAllCapsules()
        Log.d(TAG,"${response.isSuccessful}")
        if(response.isSuccessful){
            Log.d(TAG,"Fetched All Capsules Successfully ${response.body()}")
            response.body()?.let { listOfCapsules ->
                capsulesDao.replaceCapsulesData(listOfCapsules)
            }
        }else Log.e(TAG,"Unsuccessfully fetched data ${response.errorBody()}")
    }

}