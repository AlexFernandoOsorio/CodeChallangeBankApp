package com.example.codechallangebankapp.features.homeScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codechallangebankapp.core.states.AccountState
import com.example.codechallangebankapp.core.states.LoadState
import com.example.codechallangebankapp.core.utils.FlowResult
import com.example.codechallangebankapp.domain.usecases.AccountsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountsUseCase: AccountsUseCase
) : ViewModel() {


    //Se inicializa los estados de la pantalla de home
    private var _loadingState = mutableStateOf(LoadState())
    val loadingState: State<LoadState> = _loadingState
    private val _accountsState = mutableStateOf(AccountState())
    val accountsState: State<AccountState> get() = _accountsState
    private val _isLoadingSwipe = MutableStateFlow(false)
    val isLoadingSwipe = _isLoadingSwipe.asStateFlow()


    //Metodo que se encarga de realizar la petición de las cuentas
    suspend fun getAccountsList(username: String) {
        _loadingState.value = loadingState.value.copy(isLoading = true)
        accountsUseCase.getAccountsListApi(username).onEach {
            when (it) {
                is FlowResult.Loading -> {
                    //Se envía el evento de carga
                    _accountsState.value = AccountState(isLoading = true)
                    delay(3000)
                    _loadingState.value = loadingState.value.copy(isLoading = false)
                }
                is FlowResult.Success -> {
                    //Se envía el evento de éxito
                    _accountsState.value = AccountState(data = it.data)
                    delay(3000)
                    _loadingState.value = loadingState.value.copy(isLoading = false)
                }
                is FlowResult.Error -> {
                    //Se envía el evento de error
                    _accountsState.value = AccountState(error = it.message.toString())
                    delay(3000)
                    _loadingState.value = loadingState.value.copy(isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    //Metodo que se encarga de realizar la petición de las cuentas actualizadas
    private suspend fun getUpdatedAccountsList(username: String) {
        accountsUseCase.getUpdatedAccountsListApi(username).onEach {
            when (it) {
                is FlowResult.Loading -> {
                    //Se envía el evento de carga
                    _accountsState.value = AccountState(isLoading = true)
                    delay(3000)
                    _loadingState.value = loadingState.value.copy(isLoading = false)
                }
                is FlowResult.Success -> {
                    //Se envía el evento de éxito
                    _accountsState.value = AccountState(data = it.data)
                    delay(3000)
                    _loadingState.value = loadingState.value.copy(isLoading = false)
                }
                is FlowResult.Error -> {
                    //Se envía el evento de error
                    _accountsState.value = AccountState(error = it.message.toString())
                    delay(3000)
                    _loadingState.value = loadingState.value.copy(isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    //Metodo que se encarga de realizar la petición de las cuentas disparada por el refreshSwipe
    fun loadSwipeRefresh() {
        viewModelScope.launch {
            _isLoadingSwipe.value = true
            getUpdatedAccountsList("userTest1")
            delay(3000L)
            _isLoadingSwipe.value = false
        }
    }

    //Actualiza el token
    suspend fun updateToken(timer : Long){
        var newToken = "faltaServicioParaObtenerNuevoToken"
        //accountsUseCase.getToken().collectLatest{newToken= it.token}
        accountsUseCase.updateToken(newToken,timer)
    }

    fun getAccountsAgain(username: String){
        viewModelScope.launch {
            getAccountsList(username)
        }
    }
    fun getAccountsUpdatedAgain(username: String){
        viewModelScope.launch {
            getUpdatedAccountsList(username)
        }
    }
}