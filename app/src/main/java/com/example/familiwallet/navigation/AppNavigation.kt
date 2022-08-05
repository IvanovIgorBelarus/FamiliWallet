package com.example.familiwallet.navigation

import MainScreen
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.familiwallet.features.AuthScreen.AuthScreen
import com.example.familiwallet.features.splashscreen.SplashScreen


@ExperimentalAnimationApi
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
    ) {
        composable(route = Screen.SplashScreen.route) { SplashScreen(navigation = navController) }
        composable(route = Screen.AuthScreen.route) { AuthScreen(navigation = navController) }
        composable(route = Screen.MainScreen.route) { MainScreen() }
    }
}