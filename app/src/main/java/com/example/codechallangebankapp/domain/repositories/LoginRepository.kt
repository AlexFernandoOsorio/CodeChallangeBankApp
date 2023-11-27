package com.example.codechallangebankapp.domain.repositories

import com.example.codechallangebankapp.core.utils.ResourceEvent

fun interface LoginRepository {
    suspend fun loginCallApi(username: String, password: String): ResourceEvent<Unit>
}