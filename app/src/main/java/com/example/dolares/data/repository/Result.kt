package com.example.dolares.data.repository

import java.lang.Exception

sealed class Result<out T>{
    data class Success<out T>(val data: T):Result<T>()
    data class Loading(val isLoading:Boolean):Result<Nothing>()
    data class Error<T>(val message: String? = null, val exception: Exception? = null):Result<T>()
}