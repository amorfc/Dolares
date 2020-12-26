package com.example.dolares.ui.capsules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dolares.data.repository.CapsulesRepository
import kotlinx.coroutines.launch

class CapsulesViewModel(
    private val capsulesRepository: CapsulesRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            capsulesRepository.fetchCapsulesSaveToDb()
        }
    }

}