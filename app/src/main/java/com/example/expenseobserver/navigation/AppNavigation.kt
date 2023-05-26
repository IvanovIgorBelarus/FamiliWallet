package com.example.expenseobserver.navigation

import MainScreen
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expenseobserver.core.di.DependencyFeatureProvider
import com.example.navigation.Screen
import com.example.navigation.register


@ExperimentalAnimationApi
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    dependencyFeatureProvider: DependencyFeatureProvider = DependencyFeatureProvider()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
    ) {
        register(
            featureApi = dependencyFeatureProvider.splashScreen(),
            navController = navController,
            modifier = modifier
        )
        register(
            featureApi = dependencyFeatureProvider.auth(),
            navController = navController,
            modifier = modifier
        )
        register(
            featureApi = dependencyFeatureProvider.enter(),
            navController = navController,
            modifier = modifier
        )
        composable(route = Screen.MainScreen.route) { MainScreen() }
    }
}