package com.example.dolares.data.repository

import java.lang.Exception

sealed class Result<out T:Any>{
    data class Success<out T:Any>(val data: T):Result<T>()
    data class Loading(val isLoading:Boolean):Result<Nothing>()
    data class Error<T:Any>(val message: String? = null, val exception: Exception? = null):Result<T>()
}