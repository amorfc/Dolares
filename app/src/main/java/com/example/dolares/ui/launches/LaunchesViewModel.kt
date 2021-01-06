package com.example.dolares.ui.launches

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.data.repository.LaunchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LaunchesViewModel(
    private val launchesRepository: LaunchesRepository
) : ViewModel() {

    val allLaunches: MutableLiveData<List<Launch>> = MutableLiveData()

    private val _launchesLoadingStatus:LiveData<Boolean> = launchesRepository.getLaunchesLoadingStatus()
    private val _launchesSnackBarMessage:MutableLiveData<String> = launchesRepository.getLaunchesSnackBarMessage()

    fun getLaunchesLoadingStatus():LiveData<Boolean> = _launchesLoadingStatus
    fun getLaunchesSnackBarMessage():MutableLiveData<String> = _launchesSnackBarMessage

    fun getAllLaunches():LiveData<List<Launch>> = allLaunches

    init {
        viewModelScope.launch(Dispatchers.IO){
            launchesRepository.getAllLaunchesFlowFromDb()
                .collect { allLaunches.postValue(it)
                }
        }
    }

    private fun refreshData() = viewModelScope.launch { launchesRepository.fetchAllLaunchesSaveToDb() }

}