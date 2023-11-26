package com.example.codechallangebankapp.domain.mappers

import com.example.codechallangebankapp.data.remote.models.details.response.AccountsMovementsResponseDto
import com.example.codechallangebankapp.domain.models.AccountMovement

fun AccountsMovementsResponseDto.toModel(): List<AccountMovement> {
    return movimientosPorCuenta.map {
        AccountMovement(
            it.descripcion,
            it.fecha,
            it.monto
        )
    }
}