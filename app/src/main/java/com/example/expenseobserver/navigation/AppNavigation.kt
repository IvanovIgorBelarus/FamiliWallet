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
        startDestination = Screen.EnterNavGraph.route,
    ) {
        enterNavGraph(
            modifier = modifier,
            navController = navController,
            dependencyFeatureProvider = dependencyFeatureProvider
        )
        composable(route = Screen.MainScreen.route) { MainScreen() }
    }
}