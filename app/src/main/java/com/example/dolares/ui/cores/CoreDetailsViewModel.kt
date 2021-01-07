package com.example.dolares.ui.cores

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dolares.data.local.model.Core

class CoreDetailsViewModel(val core:Core) : ViewModel() {

    val selectedCore: MutableLiveData<Core> = MutableLiveData(core)

    class Factory(val core: Core):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(CoreDetailsViewModel::class.java)){
                return CoreDetailsViewModel(core) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}