package com.example.familiwallet.navigation

import com.example.familiwallet.R
import com.example.familiwallet.core.utils.AppIcons

sealed class Screen(val route: String, var title: String? = null, var icon: Int, var id: Int? = null) {
    object MainScreen : Screen("main", "главная", AppIcons.FRAME45.imageRes)
    object LoadingScreen : Screen("loading", "", AppIcons.FRAME45.imageRes)
    object StartScreen : Screen("main1", "Главная", R.drawable.ic_start_screen,1)
    object CategoryScreen : Screen("category", "Категории", R.drawable.ic_category_screen,2)
    object HistoryScreen : Screen("history", "История", R.drawable.ic_history_screen,3)
    object SettingsScreen : Screen("settings", "Настройки", R.drawable.ic_settings_screen,4)
    object SplashScreen : Screen("splash", null, AppIcons.UNKNOWN.imageRes)
    object EnterScreen : Screen("enter", null, AppIcons.UNKNOWN.imageRes)
    object AuthScreen : Screen("auth", null, AppIcons.UNKNOWN.imageRes)

    companion object{
        fun getScreen(route: String?): Screen = Screen::class.sealedSubclasses.firstOrNull { it.objectInstance?.route == route }?.objectInstance?:LoadingScreen
    }
}
