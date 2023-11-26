package com.example.codechallangebankapp.data.repositories

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

    override suspend fun loginCallApi(username: String, password: String): ResourceEvent<Unit> {
        return try {
            val response = apiService.loginUserApi(username, password)
            authPreferences.saveAuthToken(response.token)
            ResourceEvent.Success(Unit)
        }catch (e: IOException){
            //En caso de que la petición no sea exitosa se retorna un ResourceEvent.Error
            ResourceEvent.Error("${e.message}")
        }catch (e: HttpException){
            //En caso de que la petición no sea exitosa se retorna un ResourceEvent.Error
            ResourceEvent.Error("${e.message}")
        }
    }
}