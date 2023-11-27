package com.example.codechallangebankapp.data.repositories

import com.example.codechallangebankapp.core.utils.UserToken
import com.example.codechallangebankapp.data.local.AuthPreferences
import com.example.codechallangebankapp.data.remote.services.ApiService
import com.example.codechallangebankapp.domain.mappers.toModel
import com.example.codechallangebankapp.domain.models.AccountMovementModel
import com.example.codechallangebankapp.domain.models.AccountsModel
import com.example.codechallangebankapp.domain.repositories.AccountsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences
) : AccountsRepository {

    //Retorna la lista de cuentas llamando al servicio de la API - Remoto
    override suspend fun getAccountsListApi(username: String): AccountsModel {
        return apiService.getAccountsListApi(username).toModel()
    }
    //Retorna la lista de cuentas actualizada llamando al servicio de la API - Remoto
    override suspend fun getUpdatedAccountsListApi(username: String): AccountsModel {
        return apiService.getUpdatedAccountsListApi(username).toModel()
    }
    //Retorna la lista de movimientos de una cuenta llamando al servicio de la API -
    override suspend fun getAccountMovementsApi(numeroCuenta: String): List<AccountMovementModel> {
        return apiService.getAccountMovementsApi(numeroCuenta).toModel()
    }
    //Actualiza el token de autenticación - DataStore
    override suspend fun updateToken(token: String, timer: Long)  {
        authPreferences.saveAuthToken(token,timer)
    }
    //Retorna el token de autenticación - DataStore
    override fun getToken() : Flow<UserToken> {
        return authPreferences.getAuthToken()
    }
}