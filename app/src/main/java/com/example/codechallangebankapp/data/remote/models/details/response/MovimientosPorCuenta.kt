package com.example.codechallangebankapp.data.remote.models.details.response


import com.google.gson.annotations.SerializedName

data class MovimientosPorCuenta(
    @SerializedName("descripcion")
    val descripcion: String,
    @SerializedName("fecha")
    val fecha: String,
    @SerializedName("monto")
    val monto: Double
)