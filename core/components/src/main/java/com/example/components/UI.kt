package com.example.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.mylibrary.base.BaseViewModel
import com.example.data.theme.UiState

@Composable
fun ShowScreen(
    viewModel: BaseViewModel<*, *>,
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
            val errorText = (uiState as UiState.Error).exception?.message.orEmpty()
            ShowErrorDialog(text = errorText)
        }
        is UiState.Loading -> LoadingScreen()
    }
}