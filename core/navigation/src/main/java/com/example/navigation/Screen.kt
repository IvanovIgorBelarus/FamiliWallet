package com.example.navigation

import android.os.Bundle

sealed class Screen(val route: String, var title: String? = null, var icon: Int, var id: Int? = null, val stack: Stack, var args: Bundle? = null) {
    object MainScreen : Screen("main", "главная",R.drawable.ic_frame_45, stack = Stack.UNKNOWN)
    object LoadingScreen : Screen("loading", "", R.drawable.ic_frame_45, stack = Stack.UNKNOWN)
    object StartScreen : Screen("main1", "Главная", R.drawable.ic_start_screen, 1, Stack.START)
    object CategoryScreen : Screen("category", "Категории",R.drawable.ic_category_screen, 2, Stack.CATEGORY)
    object HistoryScreen : Screen("history", "История", R.drawable.ic_history_screen, 3, Stack.HISTORY)
    object SettingsScreen : Screen("settings", "Настройки", R.drawable.ic_settings_screen, 4, Stack.SETTINGS)
    object SplashScreen : Screen("splash", null, R.drawable.ic_local_grocery_store, stack = Stack.UNKNOWN)
    object EnterScreen : Screen("enter", null, R.drawable.ic_baseline_insert_photo, stack = Stack.UNKNOWN)
    object AuthScreen : Screen("auth", null, R.drawable.ic_baseline_insert_photo, stack = Stack.UNKNOWN)
    object TransactionScreen : Screen("transaction", "Ввести операцию", R.drawable.ic_baseline_insert_photo, stack = Stack.UNKNOWN)
    object NewCategoryScreen : Screen("new category", "Новая категория", R.drawable.ic_baseline_insert_photo, stack = Stack.CATEGORY)
    object WalletScreen : Screen("walletScreen", "Список кошельков",R.drawable.ic_baseline_insert_photo,  stack = Stack.START)
    object WalletSettingsScreen : Screen("walletSettingsScreen", "Настройки кошелька", R.drawable.ic_baseline_insert_photo,  stack = Stack.START)
    object TransferScreen : Screen("transferScreen", "Переводы", R.drawable.ic_baseline_insert_photo,  stack = Stack.START)

    companion object {
        fun getScreen(route: String?): Screen = Screen::class.sealedSubclasses.firstOrNull { it.objectInstance?.route == route }?.objectInstance ?: LoadingScreen
    }
}

enum class Stack {
    START,
    CATEGORY,
    HISTORY,
    SETTINGS,
    UNKNOWN
}