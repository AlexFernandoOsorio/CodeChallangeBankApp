package com.example.codechallangebankapp.data.repositories

import android.accounts.NetworkErrorException
import android.content.res.Resources.NotFoundException
import com.example.codechallangebankapp.core.utils.ApiErrorModel
import com.example.codechallangebankapp.core.utils.ClientError
import com.example.codechallangebankapp.core.utils.ResourceEvent
import com.example.codechallangebankapp.data.local.AuthPreferences
import com.example.codechallangebankapp.data.remote.services.ApiService
import com.example.codechallangebankapp.domain.repositories.LoginRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences
) : LoginRepository {

    //Realiza la petición de login a la API - Remoto
    override suspend fun loginCallApi(username: String, password: String): Result<Unit> {
        return try {
            //Recuperamos el sealed class del evento de la petición a la API
            val response = apiService.loginUserApi(username, password)
            //Guardamos el token de autenticación
            authPreferences.saveAuthToken(response.token,0L)
            //Retornamos un ResourceEvent.Success
            Result.success(Unit)
        }catch (e: IOException){
            //En caso de que la petición no sea exitosa se retorna un ResourceEvent.Error
            Result.failure(e)
        }catch (e : NetworkErrorException){
            //En caso de que la petición no sea exitosa se retorna un ResourceEvent.Error
            Result.failure(Exception("Error de red"))
        }catch (e : NotFoundException){
            //En caso de que la petición no sea exitosa se retorna un ResourceEvent.Error
            Result.failure(Exception("Error de servidor"))
        }catch (e: HttpException) {
            //En caso de que la petición no sea exitosa se retorna un ResourceEvent.Error
            val apiErrorModel = ApiErrorModel(e.code(), e.message())
            Result.failure(ClientError.ApiError(apiErrorModel))
        }
    }
}