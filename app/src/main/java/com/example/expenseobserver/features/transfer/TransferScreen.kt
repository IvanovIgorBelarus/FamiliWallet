package com.example.expenseobserver.features.transfer

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.components.WalletTemplate
import com.example.expenseobserver.core.common.ShowScreen
import com.example.expenseobserver.features.transfer.data.TransferScreenViewState
import com.example.expenseobserver.navigation.Screen

@Composable
fun TransferScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    transferViewModel: TransferViewModel = hiltViewModel()
) {

    ShowScreen(
        viewModel = transferViewModel,
        onSuccess = {
            UI(
                modifier = modifier,
                viewState = it as TransferScreenViewState,
                navigation = navigation,
                transferViewModel = transferViewModel
            )
        }
    )

    LaunchedEffect(Unit) {
        val id = Screen.TransferScreen.args?.getString("ID", "empty")
        transferViewModel.getTransferInfo(id)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun UI(
    modifier: Modifier,
    viewState: TransferScreenViewState,
    navigation: NavHostController,
    transferViewModel: TransferViewModel
) {
    val pageState = rememberPagerState(0)
    Scaffold(
        modifier = modifier,
        backgroundColor = Color.White
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                WalletTemplate(viewState.walletFrom)
            }
            item {
                HorizontalPager(
                    state = pageState,
                    pageCount = viewState.walletsTo.size
                ) { index ->
                    val currentItem = viewState.walletsTo[index]
                    WalletTemplate(currentItem)
                }
            }
        }
    }
}
