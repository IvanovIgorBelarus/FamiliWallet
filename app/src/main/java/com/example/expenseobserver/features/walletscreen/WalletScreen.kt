package com.example.expenseobserver.features.walletscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.components.WalletSettingsView
import com.example.expenseobserver.core.common.ShowScreen
import com.example.expenseobserver.features.walletscreen.data.WalletScreenViewState

@Composable
fun WalletScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    walletViewModel: WalletViewModel = hiltViewModel()
) {

    ShowScreen(
        viewModel = walletViewModel,
        onSuccess = {
            UI(
                modifier = modifier,
                viewState = it as WalletScreenViewState
            )
        }
    )

    LaunchedEffect(Unit) {
        walletViewModel.getData()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun UI(
    modifier: Modifier,
    viewState: WalletScreenViewState
) {
    Scaffold(
        modifier = modifier,
        backgroundColor = Color.White
    ) {
        LazyColumn {
            item {  }
            items(viewState.walletList) {
                WalletSettingsView(it)
            }
        }
    }
}