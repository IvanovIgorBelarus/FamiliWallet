package com.alseda.auth_impl.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.alseda.auth_api.AuthApi
import com.alseda.auth_impl.presentation.AuthScreen
import com.example.navigation.Screen
import javax.inject.Inject

class AuthImpl @Inject constructor() : AuthApi {
    override val authScreenRoute: String = Screen.AuthScreen.route

    override val mainScreenRoute: String = Screen.MainScreen.route

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(authScreenRoute) {
            AuthScreen(
                onNavigateToMain = { navController.navigate(mainScreenRoute) }
            )
        }
    }
}