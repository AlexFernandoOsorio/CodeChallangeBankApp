package com.example.codechallangebankapp.data.remote.models.login.response
import com.google.gson.annotations.SerializedName


data class LoginResponseDto(
    @SerializedName("status")
    val status: String,
    @SerializedName("token")
    val token: String
)