package com.example.codechallangebankapp.data.repositories

import com.example.codechallangebankapp.data.remote.services.ApiService
import com.example.codechallangebankapp.domain.mappers.toModel
import com.example.codechallangebankapp.domain.models.AccountMovement
import com.example.codechallangebankapp.domain.models.AccountsModel
import com.example.codechallangebankapp.domain.repositories.AccountsRepository
import javax.inject.Inject

class AccountsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AccountsRepository {

    override suspend fun getAccountsListApi(username: String): AccountsModel {
        return apiService.getAccountsListApi(username).toModel()
    }

    override suspend fun getUpdatedAccountsListApi(username: String): AccountsModel {
        return apiService.getUpdatedAccountsListApi(username).toModel()
    }

    override suspend fun getAccountMovementsApi(numeroCuenta: String): List<AccountMovement> {
        return apiService.getAccountMovementsApi(numeroCuenta).toModel()
    }
}