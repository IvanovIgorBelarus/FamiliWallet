package com.alseda.start_impl.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.alseda.start_api.StartApi
import com.example.navigation.Screen

class StartImpl : StartApi {
    override val startScreenRoute: String = Screen.StartScreen.route
    override val walletScreenRoute: String = Screen.WalletScreen.route
    override val walletSettingsScreenRoute: String = Screen.WalletSettingsScreen.route

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
       navGraphBuilder.composable(startScreenRoute){

       }
    }
}