package com.example.dolares.ui.capsules

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dolares.data.local.model.Capsule
import com.example.dolares.data.repository.CapsulesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val TAG  = "CapsulesViewModel"

class CapsulesViewModel(
    private val capsulesRepository: CapsulesRepository
) : ViewModel() {

    val allCapsules  = MutableLiveData<List<Capsule>>()

    val _capsulesDataLoadingStatus: LiveData<Boolean> = capsulesRepository.getDataLoadingStatus()
    val _capsulesSnackBarMessage: LiveData<String> = capsulesRepository.getCapsulesSnackBarMessage()

    init {
        refreshData()
        viewModelScope.launch(Dispatchers.IO){
            capsulesRepository.getAllCapsulesFlow()
                .collect{
                    allCapsules.postValue(it)
                }
        }
    }
    private fun refreshData() = viewModelScope.launch { capsulesRepository.executeRefreshData() }

}