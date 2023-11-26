package com.example.codechallangebankapp.features.loginScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codechallangebankapp.core.components.TextFieldState
import com.example.codechallangebankapp.core.states.AuthState
import com.example.codechallangebankapp.core.utils.ResourceEvent
import com.example.codechallangebankapp.domain.usecases.LoginAuthUseCase
import com.example.codechallangebankapp.features.UiState
import com.example.codechallangebankapp.features.navigation.AppScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginAuthUseCase: LoginAuthUseCase
) : ViewModel() {

    //Se inicializa el estado de la pantalla de login
    private var _loginState = mutableStateOf(AuthState())
    val loginState: State<AuthState> = _loginState

    //Se inicializa el evento de la pantalla de login
    private val _eventFlow = MutableSharedFlow<UiState>()
    val eventFlow = _eventFlow.asSharedFlow()

    //Se inicializa el estado del email
    private val _usernameState = mutableStateOf(TextFieldState())
    val usernameState: State<TextFieldState> = _usernameState
    fun setUsername(value: String) {
        _usernameState.value = usernameState.value.copy(text = value)
    }

    //Se inicializa el estado del password
    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    fun setPassword(value: String) {
        _passwordState.value = passwordState.value.copy(text = value)
    }

    fun loginUser() {
        //Corutina que realiza la petición de login
        viewModelScope.launch {
            _loginState.value = loginState.value.copy(isLoading = false)
            //Se realiza la petición de login con el email y el password al useCase
            val loginResult = loginAuthUseCase(
                username = usernameState.value.text,
                password = passwordState.value.text
            )
            _loginState.value = loginState.value.copy(isLoading = false)
            if (loginResult.userError != null) {
                _usernameState.value = usernameState.value.copy(error = loginResult.userError)
            }
            if (loginResult.passwordError != null) {
                _passwordState.value = passwordState.value.copy(error = loginResult.passwordError)
            }
            //Se valida el resultado de la petición
            when (loginResult.result) {
                is ResourceEvent.Success -> {
                    //En caso de que la petición sea exitosa se navega a la pantalla de Home
                    _eventFlow.emit(UiState.NavigateEvent(AppScreens.HomeScreen.route))
                }

                is ResourceEvent.Error -> {
                    //En caso de que la petición no sea exitosa se muestra un mensaje de error
                    _eventFlow.emit(UiState.SnackbarEvent(loginResult.result.message ?: "Error!"))
                }

                else -> {
                    //Nothing
                }
            }
        }
    }
}