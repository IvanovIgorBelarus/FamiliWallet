package com.example.expenseobserver.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.expenseobserver.core.di.DependencyFeatureProvider
import com.example.navigation.Screen
import com.example.navigation.register

fun NavGraphBuilder.enterNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    dependencyFeatureProvider: DependencyFeatureProvider
){
    navigation(
        startDestination = Screen.SplashScreen.route,
        route = Screen.EnterNavGraph.route
    ){
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
    }
}