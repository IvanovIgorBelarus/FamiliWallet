package com.example.familiwallet.navigation

import com.example.familiwallet.R
import com.example.familiwallet.core.utils.AppIcons

sealed class Screen(val route: String, var title: String? = null, var icon: Int) {
    object MainScreen : Screen("main", "главная", AppIcons.FRAME45.imageRes)
    object LoadingScreen : Screen("loading", "", AppIcons.FRAME45.imageRes)
    object StartScreen : Screen("main1", "главная", R.drawable.ic_start_screen)
    object CategoryScreen : Screen("category", "категории", R.drawable.ic_category_screen)
    object HistoryScreen : Screen("history", "история", R.drawable.ic_history_screen)
    object SettingsScreen : Screen("settings", "настройки", R.drawable.ic_settings_screen)
    object SplashScreen : Screen("splash", null, AppIcons.LOCAL_GROCERY_STORE.imageRes)
    object AuthScreen : Screen("auth", null, AppIcons.PERSON_ADD.imageRes)
}
