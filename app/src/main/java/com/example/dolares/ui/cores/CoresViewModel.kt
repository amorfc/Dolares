package com.example.dolares.ui.cores

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dolares.data.local.model.Core
import com.example.dolares.data.repository.CoresRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CoresViewModel(
    private val coresRepository: CoresRepository
) : ViewModel() {

    val allCores:MutableLiveData<List<Core>> = MutableLiveData<List<Core>>()

    val selectedCoreItem: MutableLiveData<Core> = MutableLiveData()

    val loadingStatus: MutableLiveData<Boolean> = coresRepository.loadingStatus
    val snackBarMessage: MutableLiveData<String> = coresRepository.snackBarMessage


    init {
        viewModelScope.launch(Dispatchers.IO){
            coresRepository.getAllCoresFlowFromDb()
                .collect {
                    if(it.isNullOrEmpty()) refreshCoreData()
                    allCores.postValue(it)
                }
        }
    }

    fun getAllCores():LiveData<List<Core>> = allCores

    fun refreshCoreData() = viewModelScope.launch { coresRepository.refreshData(true) }
    fun navigateSelectedCoreDetails(core: Core) {
        selectedCoreItem.value = core
    }

    fun doneNavigateDetailsScreen(){
        selectedCoreItem.value = null
    }

}