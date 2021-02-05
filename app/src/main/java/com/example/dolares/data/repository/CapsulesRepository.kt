package com.example.dolares.data.repository


import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.dolares.data.local.dao.CapsulesDao
import com.example.dolares.data.local.model.Capsule
import com.example.dolares.data.remote.SpacexApiService
import com.example.dolares.util.CAPSULE_DATA_REFRESH_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception


class CapsulesRepository(
    private val spacexApiService: SpacexApiService,
    private val capsulesDao: CapsulesDao,
    sharedPreferences: SharedPreferences
): BaseRepository(sharedPreferences){

    override val TAG = "CapsulesRepository"
    private val REFRESH_KEY = CAPSULE_DATA_REFRESH_KEY


    fun getAllCapsulesFlow():Flow<List<Capsule>> = capsulesDao.getAllCapsulesFlow()

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData(false)
    val snackBarMessage: MutableLiveData<String> = MutableLiveData()

    suspend fun executeRefreshData(){
        loadingStatus.value = true
        withContext(Dispatchers.IO){
            try {
                fetchAllCapsulesAndSaveToDb()
            }catch (e:Exception){
                when(e){
                    is IOException -> snackBarMessage.postValue(e.message)
                    else -> {
                        snackBarMessage.postValue( "Unexpected Problem Occurred")
                        Log.i(TAG,"Data couldn't refresh ${e.message}")
                    }
                }
            }
            loadingStatus.postValue(false)
        }
    }

    suspend fun refreshData(refresh:Boolean = false){
        if(!refresh){
            val isNeeded = withContext(Dispatchers.IO){
                checkIfDataRefreshNeeded(REFRESH_KEY)
            }
            if(!isNeeded){
                Log.i(TAG,"Don't needed refresh")
                return
            }
        }
        executeRefreshData()
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
            saveDataRefreshTime(REFRESH_KEY)
        }else Log.e(TAG,"Unsuccessfully fetched data ${response.errorBody()}")
    }
    
}