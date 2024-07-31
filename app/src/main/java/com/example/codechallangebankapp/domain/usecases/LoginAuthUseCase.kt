package com.example.codechallangebankapp.domain.usecases

import com.example.codechallangebankapp.domain.models.LoginResultModel
import com.example.codechallangebankapp.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginAuthUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(username: String, password: String): LoginResultModel {
        val usuarioError = if (username.isBlank()) "El usuario no puede estar vacio" else null
        val passwordError = if (password.isBlank()) "El passwword no puede estar vacio" else null
        //Se valida que el email y el password no estén vacíos
        if (usuarioError != null) {
            return LoginResultModel(
                userError = usuarioError
            )
        }
        if (passwordError != null) {
            return LoginResultModel(
                passwordError = passwordError
            )
        }
        //Se retorna el resultado de la petición realizando una llamada al repositorio
        return LoginResultModel(
            result = loginRepository.loginCallApi(username, password)
        )


        /*suspend fun handleLogin(username: String, password: String) {
            val result = loginRepository.loginCallApi(username, password)
            result
                .onSuccess {
                    // Procesar éxito
                    println("Login exitoso")
                }
                .onFailure { exception ->
                    // Manejar diferentes tipos de errores
                    when (exception) {
                        is ClientError.ApiError -> {
                            println("API Error: ${exception.apiErrorModel.message}")
                        }
                        is ClientError.LegacyError -> {
                            println("Legacy Error: ${exception.message}")
                        }
                        is NetworkErrorException -> {
                        // Manejar error de red
                        }
                        is NotFoundException -> {
                        // Manejar error de no encontrado
                        }
                        else -> {
                            println("Otro Error: ${exception.message}")
                        }
                    }
                }
        }*/
    }
}