package com.example.expenseobserver.features.walletscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.R
import com.example.expenseobserver.components.WalletSettings
import com.example.expenseobserver.components.WalletSettingsView
import com.example.expenseobserver.core.common.ShowScreen
import com.example.expenseobserver.core.data.AppIcons
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.features.dialog.ShowDeleteDialog
import com.example.expenseobserver.features.walletscreen.data.WalletScreenViewState
import com.example.expenseobserver.navigation.Screen

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
                viewState = it as WalletScreenViewState,
                navigation = navigation,
                walletViewModel = walletViewModel
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
    viewState: WalletScreenViewState,
    navigation: NavHostController,
    walletViewModel: WalletViewModel
) {
    val showDeleteDialog = remember { mutableStateOf(false) }
    var deleteItem = UIModel.WalletModel()

    ShowDeleteDialog(
        titleResId = R.string.delete_wallet_title,
        textResId = R.string.delete_wallet_description,
        openDialog = showDeleteDialog
    ) {
        walletViewModel.deleteItem(deleteItem)
    }

    Scaffold(
        modifier = modifier,
        backgroundColor = Color.White
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                WalletSettings(iconId = AppIcons.PLUS.imageRes) {
                    walletViewModel.openWalletSettings() {
                        navigation.navigate(Screen.WalletSettingsScreen.route)
                    }
                }
            }
            items(viewState.walletList) { walletModel ->
                WalletSettingsView(
                    wallet = walletModel,
                    onSettingsClick = { item ->
                        walletViewModel.openWalletSettings(item) {
                            navigation.navigate(Screen.WalletSettingsScreen.route)
                        }
                    },
                    onDeleteClick = { walletItem ->
                        deleteItem = walletItem
                        showDeleteDialog.value = true
                    }
                )
            }
        }
    }
}