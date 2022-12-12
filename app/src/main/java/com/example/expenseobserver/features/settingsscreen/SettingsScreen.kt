package com.example.expenseobserver.features.settingsscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController? = null,
    forceLoad: MutableState<Boolean>,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier,
        backgroundColor = Color.White
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

        }
    }

}