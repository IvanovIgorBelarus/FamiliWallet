package com.example.expenseobserver.features.walletscreen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.R
import com.example.components.HorizontalPagerWithIndicator
import com.example.components.OperationView
import com.example.components.WalletSettingsView
import com.example.components.WalletTemplate
import com.example.expenseobserver.core.common.ShowScreen
import com.example.data.UIModel
import com.example.expenseobserver.features.dialog.ShowDeleteDialog
import com.example.expenseobserver.features.walletscreen.data.WalletScreenViewState
import com.example.navigation.Screen

@Composable
fun WalletScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    update: MutableState<Boolean>,
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

    LaunchedEffect(update.value) {
        walletViewModel.getData()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun UI(
    modifier: Modifier,
    viewState: WalletScreenViewState,
    navigation: NavHostController,
    walletViewModel: WalletViewModel
) {
    val showDeleteDialog = remember { mutableStateOf(false) }
    val pageState = rememberPagerState()
    val currentItem = remember { mutableStateOf(viewState.walletList[0]) }
    val deleteItem = remember { mutableStateOf(UIModel.BaseModel()) }
    var currentItemOperations = walletViewModel.getFilteredOperations(currentItem.value, viewState.operationsList)

    ShowDeleteDialog(
        titleResId = if (deleteItem.value is UIModel.WalletModel) R.string.delete_wallet_title else R.string.delete_transaction_title,
        textResId = if (deleteItem.value is UIModel.WalletModel) R.string.delete_wallet_description else R.string.delete_description,
        openDialog = showDeleteDialog
    ) {
        walletViewModel.deleteItem(deleteItem.value)
    }

    Scaffold(
        modifier = modifier,
        backgroundColor = Color.White
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LazyColumn(
                modifier = Modifier.wrapContentSize()
            ) {
                item {
                    HorizontalPagerWithIndicator(
                        state = pageState,
                        pageCount = viewState.walletList.size
                    ) { index ->
                        val wallet = viewState.walletList[index]
                        WalletTemplate(wallet)
                    }
                }
                item {
                    WalletSettingsView(
                        onSettingsClick = {
                            Screen.WalletSettingsScreen.args = Bundle().apply { putParcelable("wallet", currentItem.value) }
                            navigation.navigate(Screen.WalletSettingsScreen.route)
                        },
                        onDeleteClick = {
                            deleteItem.value = currentItem.value
                            showDeleteDialog.value = true
                        },
                        onTransferClick = {
                            Screen.TransferScreen.args = Bundle().apply { putString("ID", currentItem.value.id) }
                            navigation.navigate(Screen.TransferScreen.route)
                        }
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(currentItemOperations) { operationItem ->
                    OperationView(
                        item = operationItem,
                        categoriesList = viewState.categoriesList,
                        currentItem.value
                    ) { item ->
                        deleteItem.value = item as UIModel.BaseModel
                        showDeleteDialog.value = true
                    }
                }
            }
        }
    }
    LaunchedEffect(pageState) {
        snapshotFlow { pageState.currentPage }.collect { page ->
            currentItem.value = viewState.walletList[page]
            currentItemOperations = walletViewModel.getFilteredOperations(currentItem.value, viewState.operationsList)
        }
    }
}