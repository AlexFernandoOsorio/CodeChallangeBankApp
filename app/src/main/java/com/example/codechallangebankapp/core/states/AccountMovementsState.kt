package com.example.codechallangebankapp.core.states

import com.example.codechallangebankapp.domain.models.AccountMovementModel

class AccountMovementsState (
    val isLoading: Boolean = false,
    val data: List<AccountMovementModel>? = null,
    val error: String = ""
)