package com.example.dolares.ui.capsules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dolares.data.local.model.Capsule

class CapsuleDetailsViewModel(val capsule:Capsule) : ViewModel() {
    // TODO: Implement the ViewModel

    class Factory(val capsule: Capsule):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(CapsuleDetailsViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return CapsuleDetailsViewModel(capsule = capsule) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}