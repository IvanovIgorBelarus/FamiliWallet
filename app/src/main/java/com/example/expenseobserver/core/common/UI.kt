package com.example.expenseobserver.core.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import com.example.expenseobserver.core.data.UiState
import com.example.expenseobserver.features.dialog.ShowErrorDialog
import com.example.expenseobserver.features.loading.LoadingScreen

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
            forceLoad.value = false
        }
        is UiState.Error -> {
            val errorText = (uiState as UiState.Error).exception.message
            ShowErrorDialog(text = errorText)
        }
        is UiState.Loading -> LoadingScreen()
    }
}