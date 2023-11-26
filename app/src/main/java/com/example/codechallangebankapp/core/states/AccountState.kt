package com.example.codechallangebankapp.core.states

import com.example.codechallangebankapp.domain.models.AccountsModel

data class AccountState(
    val isLoading: Boolean = false,
    val data: AccountsModel? = null,
    val error: String = ""
)
