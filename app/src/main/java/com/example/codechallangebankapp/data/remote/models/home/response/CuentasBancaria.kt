package com.example.codechallangebankapp.data.remote.models.home.response


import com.google.gson.annotations.SerializedName

data class CuentasBancaria(
    @SerializedName("numeroCuenta")
    val numeroCuenta: String,
    @SerializedName("saldo")
    val saldo: Double,
    @SerializedName("tipoCuenta")
    val tipoCuenta: String
)