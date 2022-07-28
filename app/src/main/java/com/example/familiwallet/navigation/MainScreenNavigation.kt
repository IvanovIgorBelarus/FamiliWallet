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
        composable(route = Screen.NewIncomeScreen.route) { NewTransactionScreen(navigation = navigation,text = "Вносим доход") }
        composable(route = Screen.NewExpanseScreen.route) { NewTransactionScreen(navigation = navigation,text = "Вносим расход") }
        composable(route = Screen.StatisticsScreen.route) { NewTransactionScreen(navigation = navigation,text = "Статистика") }
    }
}