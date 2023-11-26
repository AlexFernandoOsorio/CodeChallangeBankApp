package com.example.codechallangebankapp.core.utils

sealed class ResourceEvent<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ResourceEvent<T>(data)
    class Loading<T>(data: T? = null) : ResourceEvent<T>(data)
    class Error<T>(message: String, data: T? = null) : ResourceEvent<T>(data, message)
}
