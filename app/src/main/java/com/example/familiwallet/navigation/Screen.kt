package com.example.familiwallet.navigation

sealed class Screen(val route: String){
    object MainScreen: Screen ("main")
    object NewIncomeScreen: Screen ("add income")
    object NewExpanseScreen: Screen ("add expanse")
}
