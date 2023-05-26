package com.alseda.splashscreen.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.alseda.splashscreen.presentation.SplashScreen
import com.alseda.splashscreen_api.SplashScreenApi
import com.example.navigation.Screen
import javax.inject.Inject

class SplashScreenApiImpl @Inject constructor() : SplashScreenApi {

    override val splashScreenRoute: String = Screen.SplashScreen.route

    override val enterScreenRoute: String = Screen.EnterScreen.route

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(splashScreenRoute){
            SplashScreen(
                onNavigateToStartScreen = {
                    navController.popBackStack()
                    navController.navigate(enterScreenRoute)
                }
            )
        }
    }
}