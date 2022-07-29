package com.example.familiwallet.navigation

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.familiwallet.R
import com.example.familiwallet.core.utils.AppIcons

sealed class Screen(val route: String, var title: String? = null, var icon: Int){
    object MainScreen: Screen ("main", "главная", AppIcons.FRAME45.imageRes)
    object LoadingScreen: Screen ("loading", "", AppIcons.FRAME45.imageRes)
    object StartScreen: Screen ("main1", "главная", R.drawable.ic_start_screen)
    object CategoryScreen: Screen ("category", "категории",R.drawable.ic_category_screen)
    object HistoryScreen: Screen ("history", "история",R.drawable.ic_history_screen)
    object SettingsScreen: Screen ("settings", "настройки",R.drawable.ic_settings_screen)
}
