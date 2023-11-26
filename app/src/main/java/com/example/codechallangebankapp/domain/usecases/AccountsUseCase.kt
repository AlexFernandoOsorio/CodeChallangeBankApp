package com.example.codechallangebankapp.domain.usecases

import com.example.codechallangebankapp.core.utils.FlowResult
import com.example.codechallangebankapp.domain.models.AccountMovement
import com.example.codechallangebankapp.domain.models.AccountsModel
import com.example.codechallangebankapp.domain.repositories.AccountsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountsUseCase @Inject constructor(
    private val repository: AccountsRepository
) {
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

    suspend fun getAccountMovementsApi(numeroCuenta: String) = flow<FlowResult<List<AccountMovement>>> {
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
}