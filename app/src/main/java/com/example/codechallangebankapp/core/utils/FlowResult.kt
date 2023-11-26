package com.example.codechallangebankapp.core.utils

sealed class FlowResult<T>(val data: T? = null, val message: String? = null){
    class Loading<T>(): FlowResult<T>()
    class Success<T>(data: T): FlowResult<T>(data = data)
    class Error<T>(message: String): FlowResult<T>(message = message)
}