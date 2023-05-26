package com.alseda.enter_impl.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.alseda.enter_api.EnterApi
import com.alseda.enter_impl.presentation.EnterScreen
import com.example.common.utils.UserUtils
import com.example.navigation.Screen
import javax.inject.Inject

class EnterImpl @Inject constructor() : EnterApi {
    override val enterScreenRoute: String = Screen.EnterScreen.route

    override val mainScreenRoute: String = Screen.MainScreen.route

    override val authScreenRoute: String = Screen.AuthScreen.route


    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(enterScreenRoute) {
            EnterScreen(
                onNavigateToNextScreen = {
                    val nextDestination = if (UserUtils.isUserSignIn()) mainScreenRoute else authScreenRoute
                    navController.navigate(nextDestination)
                }
            )
        }
    }
}