package com.example.familiwallet.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, var title: String? = null, var icon: ImageVector){
    object MainScreen: Screen ("main", "главная", Icons.Default.Home)
    object NewIncomeScreen: Screen ("add income", "доходы", Icons.Default.Add)
    object NewExpanseScreen: Screen ("add expanse", "расходы", Icons.Default.Delete)
    object StatisticsScreen: Screen ("statistics", "статистика", Icons.Default.Settings)
}
