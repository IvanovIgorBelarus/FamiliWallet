package com.example.expenseobserver.navigation

import MainScreen
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expenseobserver.features.authScreen.AuthScreen
import com.example.expenseobserver.features.enterscreen.EnterScreen
import com.example.expenseobserver.features.splashscreen.SplashScreen


@ExperimentalAnimationApi
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
    ) {
        composable(route = Screen.SplashScreen.route) { SplashScreen(navigation = navController) }
        composable(route = Screen.EnterScreen.route) { EnterScreen(navigation = navController) }
        composable(route = Screen.AuthScreen.route) { AuthScreen(navigation = navController) }
        composable(route = Screen.MainScreen.route) { MainScreen() }
    }
}