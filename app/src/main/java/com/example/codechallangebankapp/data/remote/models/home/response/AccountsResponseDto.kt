package com.example.codechallangebankapp.data.remote.models.home.response


import com.google.gson.annotations.SerializedName

data class AccountsResponseDto(
    @SerializedName("clienteId")
    val clienteId: String,
    @SerializedName("cuentasBancarias")
    val cuentasBancarias: List<CuentasBancaria>,
    @SerializedName("nombreCliente")
    val nombreCliente: String
)