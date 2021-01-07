package com.example.dolares.ui.launches

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.data.repository.LaunchDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LaunchDetailsViewModel(
    private val launchDetailsRepository: LaunchDetailsRepository
    ) : ViewModel() {




    val selectedLaunch: MutableLiveData<Launch?> = MutableLiveData()

    fun fetchSelectedLaunch(launchId: String) = viewModelScope.launch(Dispatchers.IO) {
        launchDetailsRepository.getALaunch(launchId)
            .collect {
                Log.i("LaunchDetailsViewMode",it.toString())
                selectedLaunch.postValue(it)
            }
    }

}