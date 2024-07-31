package com.example.codechallangebankapp.domain.repositories


fun interface LoginRepository {
    suspend fun loginCallApi(username: String, password: String): Result<Unit>
}