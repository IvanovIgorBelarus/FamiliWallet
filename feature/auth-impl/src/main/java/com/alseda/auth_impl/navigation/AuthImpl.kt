package com.alseda.auth_impl.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.alseda.auth_api.AuthApi
import com.alseda.auth_impl.presentation.AuthScreen
import com.example.navigation.Screen
import javax.inject.Inject

class AuthImpl @Inject constructor(): AuthApi {
    override val mainScreen: String = Screen.MainScreen.route

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(mainScreen){
            AuthScreen(
                onNavigateToMain = {navController.navigate(Screen.MainScreen.route)}
            )
        }
    }
}