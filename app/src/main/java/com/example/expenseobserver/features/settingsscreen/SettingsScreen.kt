package com.example.expenseobserver.features.settingsscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
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
        Text(
            text = "coming soon",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxSize()
        )
    }

}