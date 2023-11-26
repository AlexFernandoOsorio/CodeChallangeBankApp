package com.example.codechallangebankapp.domain.models

import com.example.codechallangebankapp.core.utils.ResourceEvent

data class LoginResultModel(
    val passwordError: String? = null,
    val userError: String? = null,
    val result: ResourceEvent<Unit>? = null
)