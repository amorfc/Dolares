package com.example.dolares.ui.capsules

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dolares.data.repository.CapsulesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val TAG  = "CapsulesViewModel"

class CapsulesViewModel(
    private val capsulesRepository: CapsulesRepository
) : ViewModel() {

    fun refreshData() = viewModelScope.launch { capsulesRepository.fetchCapsulesSaveToDb() }
}