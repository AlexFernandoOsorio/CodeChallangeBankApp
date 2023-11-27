package com.example.codechallangebankapp.domain.repositories

import com.example.codechallangebankapp.core.utils.UserToken
import com.example.codechallangebankapp.domain.models.AccountMovementModel
import com.example.codechallangebankapp.domain.models.AccountsModel
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {
    suspend fun getAccountsListApi(username: String): AccountsModel
    suspend fun getUpdatedAccountsListApi(username: String): AccountsModel
    suspend fun getAccountMovementsApi(numeroCuenta: String): List<AccountMovementModel>
    suspend fun updateToken(token: String, timer: Long)
    fun getToken(): Flow<UserToken>
}