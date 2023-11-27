package com.example.codechallangebankapp.domain.usecases

import com.example.codechallangebankapp.core.utils.FlowResult
import com.example.codechallangebankapp.domain.models.AccountMovementModel
import com.example.codechallangebankapp.domain.models.AccountsModel
import com.example.codechallangebankapp.domain.repositories.AccountsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountsUseCase @Inject constructor(
    private val repository: AccountsRepository
) {
    //Se realiza la petición de las cuentas
    suspend fun getAccountsListApi(username: String) = flow<FlowResult<AccountsModel>> {
        emit(FlowResult.Loading())
        val accountsList = runCatching {
            repository.getAccountsListApi(username)
        }
        accountsList.onSuccess {
            emit(FlowResult.Success(it))
        }.onFailure {
            emit(FlowResult.Error(it.message.toString()))
        }
    }
    //Se realiza la petición de las cuentas actualizadas
    suspend fun getUpdatedAccountsListApi(username: String) = flow<FlowResult<AccountsModel>> {
        emit(FlowResult.Loading())
        val accountsList = runCatching {
            repository.getUpdatedAccountsListApi(username)
        }
        accountsList.onSuccess {
            emit(FlowResult.Success(it))
        }.onFailure {
            emit(FlowResult.Error(it.message.toString()))
        }
    }
    //Se realiza la petición de los movimientos de la cuenta
    suspend fun getAccountMovementsApi(numeroCuenta: String) = flow<FlowResult<List<AccountMovementModel>>> {
        emit(FlowResult.Loading())
        val accountMovements = runCatching {
            repository.getAccountMovementsApi(numeroCuenta)
        }
        accountMovements.onSuccess {
            emit(FlowResult.Success(it))
        }.onFailure {
            emit(FlowResult.Error(it.message.toString()))
        }
    }
    //Se realiza la petición de actualizar el token
    suspend fun updateToken(token: String, timer: Long) {
        repository.updateToken(token,timer)
    }

    fun getToken() = repository.getToken()
}