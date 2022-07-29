package com.example.familiwallet.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.familiwallet.features.newcategory.NewTransactionScreen
import com.example.familiwallet.features.start_screen.StartScreen

@Composable
fun MainScreenNavigation(navigation: NavHostController){
    NavHost(navController = navigation, startDestination = Screen.StartScreen.route) {
        composable(route = Screen.StartScreen.route) {StartScreen(navigation = navigation)}
        composable(route = Screen.CategoryScreen.route) { NewTransactionScreen(navigation = navigation,text = "Категории") }
        composable(route = Screen.HistoryScreen.route) { NewTransactionScreen(navigation = navigation,text = "История") }
        composable(route = Screen.SettingsScreen.route) { NewTransactionScreen(navigation = navigation,text = "Настройки") }
    }
}