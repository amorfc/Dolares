package com.example.dolares.data.repository

sealed class Result<T>(
    var data:T? = null,
    var message:String? = null
) {
    class Success<T>(data: T):Result<T>(data = data)
    class Loading<T>(data: T?):Result<T>(data = data)
    class Error<T>(message: String, data: T? = null):Result<T>(message = message,data = data)
}