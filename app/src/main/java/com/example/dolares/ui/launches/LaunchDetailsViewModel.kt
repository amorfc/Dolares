package com.example.dolares.ui.launches

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.data.local.model.launch.LaunchToNotify
import com.example.dolares.data.repository.LaunchDetailsRepository
import com.example.dolares.util.COUNT_DOWN_INTERVAL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LaunchDetailsViewModel(
    private val launchDetailsRepository: LaunchDetailsRepository
) : ViewModel() {


//    private val _isUpcoming: MutableLiveData<Boolean> = MutableLiveData(false)
//    val isUpcoming:LiveData<Boolean>
//        get() = _isUpcoming

    private val _timer: MutableLiveData<Long> = MutableLiveData(0L)
    val timer: LiveData<Long>
        get() = _timer

    val selectedLaunch: MutableLiveData<Launch?> = MutableLiveData()

    private val _isLaunchNotifyActive: MutableLiveData<Boolean> = MutableLiveData()
    val isLaunchNotifyActive:LiveData<Boolean>
        get() = _isLaunchNotifyActive

    fun fetchSelectedLaunch(launchId: String) = viewModelScope.launch(Dispatchers.IO) {
        launchDetailsRepository.getALaunch(launchId)
            .collect {
                Log.i("LaunchDetailsViewMode", it.toString())
                selectedLaunch.postValue(it)
                if (it != null && it.upcoming == true) {
                    viewModelScope.launch(Dispatchers.Main) {
                        setTimer(it)
                    }
                }
                //Notify Status Handling
                handleALaunchNotifyStatus(launchId)
            }
    }

    private suspend fun handleALaunchNotifyStatus(launchId: String){
        val launchToNotify:LaunchToNotify?  = launchDetailsRepository.getIfNotifyLaunchExist(launchId)
        launchToNotify?.let {
            _isLaunchNotifyActive.postValue(launchToNotify.isNotifyActive)
            return
        }
        _isLaunchNotifyActive.postValue(false)
    }

    private fun setTimer(launch: Launch) {

        val launchTimeMillis = launch.dateUnix!!.toLong() * 1000
        val currentTimeMillis = System.currentTimeMillis()

        val launchCountDownMillis = launchTimeMillis!! - currentTimeMillis

        val timer = object : CountDownTimer(launchCountDownMillis, COUNT_DOWN_INTERVAL) {

            override fun onTick(millisUntilFinished: Long) {
                _timer.postValue(millisUntilFinished / 1000)
            }

            override fun onFinish() {
                _timer.postValue(0L)
            }
        }
        timer.start()
    }

    fun changeNotifyStatus(launchId: String) = viewModelScope.launch {

        val launchToNotify:LaunchToNotify? = launchDetailsRepository.getIfNotifyLaunchExist(launchId)

        launchToNotify?.let {

            launchDetailsRepository.setLaunchNotifyStatus(launchId,!launchToNotify.isNotifyActive)
            return@launch
        }

        launchDetailsRepository.setLaunchNotifyStatus(launchId,true)

        return@launch
    }
}