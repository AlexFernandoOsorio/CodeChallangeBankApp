package com.example.codechallangebankapp.data.remote.services

import com.example.codechallangebankapp.core.utils.Constants.END_POINT_ACCOUNT
import com.example.codechallangebankapp.core.utils.Constants.END_POINT_ACCOUNT_MOVEMENTS
import com.example.codechallangebankapp.core.utils.Constants.END_POINT_ACCOUNT_UPDATED
import com.example.codechallangebankapp.core.utils.Constants.END_POINT_LOGIN
import com.example.codechallangebankapp.data.remote.models.details.response.AccountsMovementsResponseDto
import com.example.codechallangebankapp.data.remote.models.home.response.AccountsResponseDto
import com.example.codechallangebankapp.data.remote.models.login.response.LoginResponseDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //Se realiza la peticion POST para el login
    @POST("$END_POINT_LOGIN/{username}/{password}")
    suspend fun loginUserApi(@Path("username") username:String, @Path("password") password:String): LoginResponseDto
    //Se realiza la peticion GET para obtener los datos de una lista de recursos
    @GET(END_POINT_ACCOUNT)
    suspend fun getAccountsListApi(@Query("nombreCliente") nombreCliente :String) : AccountsResponseDto
    //Se realiza la peticion GET para obtener los datos de una lista de recursos
    @GET(END_POINT_ACCOUNT_UPDATED)
    suspend fun getUpdatedAccountsListApi(@Query("nombreCliente") nombreCliente :String) : AccountsResponseDto
    @GET(END_POINT_ACCOUNT_MOVEMENTS)
    suspend fun getAccountMovementsApi(@Query("numeroCuenta") numeroCuenta :String) : AccountsMovementsResponseDto

}