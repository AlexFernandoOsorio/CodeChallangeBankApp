package com.example.codechallangebankapp.data.remote.models.details.response


import com.google.gson.annotations.SerializedName

data class AccountsMovementsResponseDto(
    @SerializedName("clienteId")
    val clienteId: String,
    @SerializedName("movimientosPorCuenta")
    val movimientosPorCuenta: List<MovimientosPorCuenta>,
    @SerializedName("nombreCliente")
    val nombreCliente: String
)