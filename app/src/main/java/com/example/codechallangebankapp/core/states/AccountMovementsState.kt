package com.example.codechallangebankapp.core.states

import com.example.codechallangebankapp.domain.models.AccountMovement

class AccountMovementsState (
    val isLoading: Boolean = false,
    val data: List<AccountMovement>? = null,
    val error: String = ""
)