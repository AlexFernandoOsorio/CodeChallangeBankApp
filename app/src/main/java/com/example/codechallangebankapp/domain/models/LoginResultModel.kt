package com.example.codechallangebankapp.domain.models

data class LoginResultModel(
    val passwordError: String? = null,
    val userError: String? = null,
    val result: Result<Unit>? = null
)