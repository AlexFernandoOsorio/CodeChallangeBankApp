package com.example.codechallangebankapp.features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.codechallangebankapp.features.detailScreen.DetailScreen
import com.example.codechallangebankapp.features.homeScreen.HomeScreen
import com.example.codechallangebankapp.features.loginScreen.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route) {
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = AppScreens.HomeScreen.route + "/{username}",
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                }
            )
        ) {
            val usernameParam = it.arguments?.getString("username") ?: ""
            HomeScreen(navController, usernameParam)
        }
        composable(route = AppScreens.DetailScreen.route + "/{nameAccount}" + "/{amountAccount}" + "/{numberAccount}",
            arguments = listOf(
                navArgument("nameAccount") {
                    type = NavType.StringType
                },
                navArgument("amountAccount") {
                    type = NavType.StringType
                },
                navArgument("numberAccount") {
                    type = NavType.StringType
                }
            )
        ) {
            val nameAccountParam = it.arguments?.getString("nameAccount") ?: ""
            val amountAccountParam = it.arguments?.getString("amountAccount") ?: ""
            val numberAccountParam = it.arguments?.getString("numberAccount") ?: ""
            DetailScreen(navController, nameAccountParam, amountAccountParam, numberAccountParam)
        }
    }
}