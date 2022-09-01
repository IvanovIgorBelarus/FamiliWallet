package com.example.familiwallet.features.start_screen

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.familiwallet.components.TransactionRow
import com.example.familiwallet.core.common.EXPENSES
import com.example.familiwallet.core.ui.UiState
import com.example.familiwallet.features.diagram.DiagramScreen
import com.example.familiwallet.features.dialog.ShowDialog
import com.example.familiwallet.features.loading.LoadingScreen

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    startViewModel: StartViewModel = hiltViewModel()
) {
    val uiState by startViewModel.getUiState()

    when (uiState) {
        is UiState.Success -> {
            val viewState = (uiState as UiState.Success<StartScreenViewState>).data
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn() {
                    item {
                        DiagramScreen(
                            modifier = modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 500.dp),
                            expansesList = viewState.transactionsList.filter { it.type == EXPENSES },
                            categoriesList = viewState.categoriesList
                        )
                    }
                    items(viewState.transactionsList) { item ->
                        TransactionRow(
                            transaction = item,
                            categoriesList = viewState.categoriesList
                        )
                    }
                }
            }
        }
        is UiState.Error -> {
            val errorText = (uiState as UiState.Error).exception.message
            ShowDialog(text = errorText)
        }
        is UiState.Loading -> {
            LoadingScreen()
        }
    }
}