package com.example.codechallangebankapp.domain.repositories

import com.example.codechallangebankapp.domain.models.AccountMovement
import com.example.codechallangebankapp.domain.models.AccountsModel

interface AccountsRepository {
    suspend fun getAccountsListApi(username: String): AccountsModel
    suspend fun getUpdatedAccountsListApi(username: String): AccountsModel
    suspend fun getAccountMovementsApi(numeroCuenta: String): List<AccountMovement>
}