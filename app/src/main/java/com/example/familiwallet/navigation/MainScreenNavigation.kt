package com.example.familiwallet.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.familiwallet.features.newcategory.NewTransactionScreen
import com.example.familiwallet.features.start_screen.StartScreen
import com.example.familiwallet.features.transacrionscreen.TransactionScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
@Composable
fun MainScreenNavigation(navigation: NavHostController) {
    AnimatedNavHost(
        navController = navigation,
        startDestination = Screen.StartScreen.route,
        enterTransition = { slideIntoContainer(getSlideDirection(initialState, targetState), animationSpec = tween(500)) }
    ) {
        composable(route = Screen.StartScreen.route) { StartScreen(navigation = navigation) }
        composable(route = Screen.CategoryScreen.route) { NewTransactionScreen(navigation = navigation, text = "Категории") }
        composable(route = Screen.HistoryScreen.route) { NewTransactionScreen(navigation = navigation, text = "История") }
        composable(route = Screen.SettingsScreen.route) { NewTransactionScreen(navigation = navigation, text = "Настройки") }
        composable(route = Screen.TransactionScreen.route) { TransactionScreen(navigation = navigation) }
    }
}

@ExperimentalAnimationApi
private fun getSlideDirection(
    initialState: NavBackStackEntry,
    targetState: NavBackStackEntry
): AnimatedContentScope.SlideDirection {
    val initialScreen = Screen.getScreen(initialState.destination.route)
    val targetScreen = Screen.getScreen(targetState.destination.route)

    return if ((initialScreen.id ?: 0) < (targetScreen.id ?: 0)) {
        AnimatedContentScope.SlideDirection.Start
    } else {
        AnimatedContentScope.SlideDirection.End
    }
}
