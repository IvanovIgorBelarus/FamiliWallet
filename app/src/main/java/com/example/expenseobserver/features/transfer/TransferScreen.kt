package com.example.expenseobserver.features.transfer

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.core.common.ShowScreen
import com.example.expenseobserver.features.transfer.data.TransferScreenViewState

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
        transferViewModel.getData()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun UI(
    modifier: Modifier,
    viewState: TransferScreenViewState,
    navigation: NavHostController,
    transferViewModel: TransferViewModel
) {
    Scaffold(
        modifier = modifier,
        backgroundColor = Color.White
    ) {}
}