package com.example.codechallangebankapp.features.detailScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codechallangebankapp.core.states.AccountMovementsState
import com.example.codechallangebankapp.core.states.LoadState
import com.example.codechallangebankapp.core.utils.FlowResult
import com.example.codechallangebankapp.domain.usecases.AccountsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val accountsUseCase: AccountsUseCase
) : ViewModel() {

    private var _loadingState = mutableStateOf(LoadState())
    val loadingState: State<LoadState> = _loadingState

    private val _accountsMovementsState = mutableStateOf(AccountMovementsState())
    val accountsMovementsState: State<AccountMovementsState> get() = _accountsMovementsState


    suspend fun getAccountMovementsList(numberAccount: String) {
        _loadingState.value = loadingState.value.copy(isLoading = true)
        accountsUseCase.getAccountMovementsApi(numberAccount).onEach {
            when (it) {
                is FlowResult.Loading -> {
                    //Se envía el evento de carga
                    _accountsMovementsState.value = AccountMovementsState(isLoading = true)
                    delay(3000)
                    _loadingState.value = loadingState.value.copy(isLoading = false)
                }

                is FlowResult.Success -> {
                    //Se envía el evento de éxito
                    _accountsMovementsState.value = AccountMovementsState(data = it.data)
                    delay(3000)
                    _loadingState.value = loadingState.value.copy(isLoading = false)
                }

                is FlowResult.Error -> {
                    //Se envía el evento de error
                    _accountsMovementsState.value = AccountMovementsState(error = it.message.toString())
                    delay(3000)
                    _loadingState.value = loadingState.value.copy(isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    suspend fun updateToken(timer : Long){
        var newToken = "faltaServicioParaObtenerNuevoToken"
        /*accountsUseCase.getToken().collectLatest{
            newToken= it.token
        }*/
        accountsUseCase.updateToken(newToken,timer)
    }
}