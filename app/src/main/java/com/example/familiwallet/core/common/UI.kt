package com.example.familiwallet.core.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import com.example.familiwallet.core.ui.UiState
import com.example.familiwallet.features.dialog.ShowDialog
import com.example.familiwallet.features.loading.LoadingScreen

@Composable
fun ShowScreen(
    viewModel: BaseViewModel<*>,
    forceLoad: MutableState<Boolean>,
    onSuccess: @Composable (Any?) -> Unit = {},
    onError: @Composable () -> Unit = {},
    onLoading: @Composable () -> Unit = {},
) {
    val uiState by viewModel.getUiState()

    when (uiState) {
        is UiState.Success<*> -> {
            onSuccess.invoke((uiState as UiState.Success<*>).data)
        }
        is UiState.Error -> {
            val errorText = (uiState as UiState.Error).exception.message
            ShowDialog(text = errorText)
        }
        is UiState.Loading -> LoadingScreen()
    }

    if (forceLoad.value) {
        LaunchedEffect(Unit) {
            viewModel.getData(forceLoad.value)
            forceLoad.value = false
        }
    }
}