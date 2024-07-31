package com.example.codechallangebankapp.core.utils

sealed class ClientError : Throwable() {
    data class ApiError(val apiErrorModel: ApiErrorModel) : ClientError()
    object LegacyError : ClientError()
}

data class ApiErrorModel(val code: Int, val message: String)