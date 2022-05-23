package com.example.familiwallet.navigation

import MainScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.familiwallet.features.newcategory.NewCategoryScreen


@Composable
fun FamilyWalletApp(navigation: NavHostController) {

    NavHost(navController = navigation, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) { MainScreen(navigation = navigation) }

        composable(route = Screen.NewCategoryScreen.route) { NewCategoryScreen() }
    }
}