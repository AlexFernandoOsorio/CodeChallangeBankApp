package com.example.codechallangebankapp.features.navigation

sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("login_screen")
    object HomeScreen : AppScreens("home_screen")
    object DetailScreen : AppScreens("detail_screen")
}