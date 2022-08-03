package com.example.familiwallet.navigation

import MainScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.familiwallet.features.AuthScreen.AuthScreen
import com.example.familiwallet.features.splashscreen.SplashScreen
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(
    navigation: NavHostController = rememberNavController(),
    googleSignInClient: GoogleSignInClient,
    auth: FirebaseAuth
) {
    NavHost(navController = navigation, startDestination = Screen.SplashScreen.route) {
        composable(route = Screen.SplashScreen.route) { SplashScreen(navigation = navigation) }
        composable(route = Screen.AuthScreen.route) { AuthScreen(navigation = navigation, googleSignInClient, auth) }
        composable(route = Screen.MainScreen.route) { MainScreen() }
    }
}