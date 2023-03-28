package com.example.expenseobserver.features.walletsettings

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.core.common.ShowScreen

@Composable
fun WalletSettingsScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    walletSettingsViewModel: WalletSettingsViewModel = hiltViewModel()
) {

    ShowScreen(
        viewModel = walletSettingsViewModel,
        onSuccess = {
            UI(
                modifier = modifier,
                viewState = it as WalletSettingsViewState,
                navigation = navigation
            )
        }
    )

    LaunchedEffect(Unit) {
        walletSettingsViewModel.getData()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun UI(
    modifier: Modifier,
    viewState: WalletSettingsViewState,
    navigation: NavHostController
) {
    Scaffold(
        modifier = modifier,
        backgroundColor = Color.White
    ) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {

        }
    }
}