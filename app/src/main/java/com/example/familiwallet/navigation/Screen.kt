package com.example.familiwallet.navigation

sealed class Screen(val route: String){
    object MainScreen: Screen ("main")
    object NewCategoryScreen: Screen ("new Category")
}
