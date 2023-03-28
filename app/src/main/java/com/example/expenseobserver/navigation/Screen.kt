package com.example.expenseobserver.navigation

import com.example.expenseobserver.R
import com.example.expenseobserver.core.data.AppIcons

sealed class Screen(val route: String, var title: String? = null, var icon: Int, var id: Int? = null, val stack: Stack) {
    object MainScreen : Screen("main", "главная", AppIcons.FRAME45.imageRes, stack = Stack.UNKNOWN)
    object LoadingScreen : Screen("loading", "", AppIcons.FRAME45.imageRes, stack = Stack.UNKNOWN)
    object StartScreen : Screen("main1", "Главная", R.drawable.ic_start_screen, 1, Stack.START)
    object CategoryScreen : Screen("category", "Категории", R.drawable.ic_category_screen, 2, Stack.CATEGORY)
    object HistoryScreen : Screen("history", "История", R.drawable.ic_history_screen, 3, Stack.HISTORY)
    object SettingsScreen : Screen("settings", "Настройки", R.drawable.ic_settings_screen, 4, Stack.SETTINGS)
    object SplashScreen : Screen("splash", null, AppIcons.LOCAL_GROCERY_STORE.imageRes, stack = Stack.UNKNOWN)
    object EnterScreen : Screen("enter", null, AppIcons.UNKNOWN.imageRes, stack = Stack.UNKNOWN)
    object AuthScreen : Screen("auth", null, AppIcons.UNKNOWN.imageRes, stack = Stack.UNKNOWN)
    object TransactionScreen : Screen("transaction", "Ввести операцию", AppIcons.UNKNOWN.imageRes, stack = Stack.UNKNOWN)
    object NewCategoryScreen : Screen("new category", "Новая категория", AppIcons.UNKNOWN.imageRes, stack = Stack.CATEGORY)

    object WalletScreen : Screen("walletScreen", "Список кошельков", AppIcons.UNKNOWN.imageRes, 1, stack = Stack.START)

    object WalletSettingsScreen : Screen("walletSettingsScreen", "Настройки кошелька", AppIcons.UNKNOWN.imageRes, 1, stack = Stack.START)

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
