package com.example.codechallangebankapp.data.repositories

import com.example.codechallangebankapp.core.utils.ResourceEvent
import com.example.codechallangebankapp.data.local.AuthPreferences
import com.example.codechallangebankapp.data.remote.services.ApiService
import com.example.codechallangebankapp.domain.repositories.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences
) : LoginRepository {

    //Se realiza la petición al servicio de login
    override suspend fun loginCallApi(username: String, password: String): ResourceEvent<Unit> {
        return try {
            //Recuperamos el sealed class del evento de la petición a la API
            val response = apiService.loginUserApi(username, password)
            //Guardamos el token de autenticación
            authPreferences.saveAuthToken(response.token,0L)
            //Retornamos un ResourceEvent.Success
            ResourceEvent.Success(Unit)
        }catch (e: IOException){
            //En caso de que la petición no sea exitosa se retorna un ResourceEvent.Error
            ResourceEvent.Error("${e.message}")
        }catch (e: HttpException){
            //En caso de que la petición no sea exitosa se retorna un ResourceEvent.Error
            ResourceEvent.Error("Usuario y/o contraseña incorrectos")
        }
    }
}