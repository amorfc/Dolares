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


class CapsulesViewModel(
    private val capsulesRepository: CapsulesRepository
) : ViewModel() {

    private val TAG  = "CapsulesViewModel"
    val allCapsules  = MutableLiveData<List<Capsule>>()

    val loadingStatus: MutableLiveData<Boolean> = capsulesRepository.loadingStatus
    val snackBarMessage: MutableLiveData<String> = capsulesRepository.snackBarMessage


    init {
        viewModelScope.launch(Dispatchers.IO){
            capsulesRepository.getAllCapsulesFlow()
                .collect{
                    allCapsules.postValue(it)
                    Log.d("CapsulesViewModel","Fetched All Capsules Successfully ${it}")
                }
        }
    }
    fun refreshData() = viewModelScope.launch { capsulesRepository.refreshData(true) }

}