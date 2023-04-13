package com.example.expenseobserver.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.expenseobserver.features.category.CategoryScreen
import com.example.expenseobserver.features.historyscreen.HistoryScreen
import com.example.expenseobserver.features.newcategory.NewCategoryScreen
import com.example.expenseobserver.features.settingsscreen.SettingsScreen
import com.example.expenseobserver.features.start_screen.StartScreen
import com.example.expenseobserver.features.transfer.TransferScreen
import com.example.expenseobserver.features.walletscreen.WalletScreen
import com.example.expenseobserver.features.walletsettings.WalletSettingsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
@Composable
fun MainScreenNavigation(
    navigation: NavHostController,
    modifier: Modifier = Modifier,
    update: MutableState<Boolean>,
    currentState: MutableState<Int> = mutableStateOf(0)
) {
    AnimatedNavHost(
        navController = navigation,
        startDestination = Screen.StartScreen.route,
        enterTransition = { slideIntoContainer(getSlideDirection(initialState, targetState), animationSpec = tween(500)) }
    ) {
        composable(route = Screen.StartScreen.route) { StartScreen(modifier, navigation, update) }
        composable(route = Screen.CategoryScreen.route) { CategoryScreen(modifier, navigation, currentState) }
        composable(route = Screen.HistoryScreen.route) { HistoryScreen(modifier, navigation, update) }
        composable(route = Screen.SettingsScreen.route) { SettingsScreen(modifier, navigation) }
        composable(route = Screen.NewCategoryScreen.route) { NewCategoryScreen(modifier, navigation) }
        composable(route = Screen.WalletScreen.route) { WalletScreen(modifier, navigation) }
        composable(route = Screen.WalletSettingsScreen.route) { WalletSettingsScreen(modifier, navigation) }
        composable(route = Screen.TransferScreen.route) { TransferScreen(modifier, navigation) }
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
