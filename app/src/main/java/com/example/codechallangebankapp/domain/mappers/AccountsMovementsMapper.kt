package com.example.codechallangebankapp.domain.mappers

import com.example.codechallangebankapp.data.remote.models.details.response.AccountsMovementsResponseDto
import com.example.codechallangebankapp.domain.models.AccountMovementModel

//Convierte el objeto de respuesta de la API a un modelo de datos
fun AccountsMovementsResponseDto.toModel(): List<AccountMovementModel> {
    return movimientosPorCuenta.map {
        AccountMovementModel(
            it.descripcion,
            it.fecha,
            it.monto
        )
    }
}