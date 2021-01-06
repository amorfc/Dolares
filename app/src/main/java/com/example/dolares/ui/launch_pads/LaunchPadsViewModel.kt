package com.example.dolares.ui.launch_pads

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dolares.data.local.model.LaunchPad
import com.example.dolares.data.repository.LaunchPadsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LaunchPadsViewModel(
    private val launchPadsRepository: LaunchPadsRepository
) : ViewModel() {

    val allLaunchPads:MutableLiveData<List<LaunchPad>> = MutableLiveData()
    private val _launchPadsLoadingStatus: LiveData<Boolean> = launchPadsRepository.getLaunchPadsLoadingStatus()
    private val _launchPadsSnackBarMessage: MutableLiveData<String> = launchPadsRepository.getLaunchPadsSnackBarMessage()

    fun getLaunchPadsLoadingStatus():LiveData<Boolean> = _launchPadsLoadingStatus
    fun getLaunchPadsSnackBarMessage():LiveData<String> = _launchPadsSnackBarMessage

    fun getAllLaunchPads(): LiveData<List<LaunchPad>> = allLaunchPads

    init {
        viewModelScope.launch {
            launchPadsRepository.getAllLaunchPadsFlowFromDb()
                .collect { allLaunchPads.postValue(it)
                    Log.d("LaunchPadsViewModel","Fetched All Capsules Successfully ${it}")
                }
        }
    }

    fun refreshData() = viewModelScope.launch { launchPadsRepository.fetchLaunchPadsSaveToDb() }

}