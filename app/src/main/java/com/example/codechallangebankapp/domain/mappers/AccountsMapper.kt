package com.example.codechallangebankapp.domain.mappers

import com.example.codechallangebankapp.data.remote.models.home.response.AccountsResponseDto
import com.example.codechallangebankapp.domain.models.Account
import com.example.codechallangebankapp.domain.models.AccountsModel

//Convierte el objeto de respuesta de la API a un modelo de datos
fun AccountsResponseDto.toModel(): AccountsModel {
    return AccountsModel(
        cuentasBancarias = cuentasBancarias.map {
            Account(
                it.numeroCuenta,
                it.saldo,
                it.tipoCuenta
            ) },
        nombreCliente = nombreCliente
    )
}