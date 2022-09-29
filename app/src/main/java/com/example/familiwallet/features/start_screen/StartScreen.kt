package com.example.familiwallet.features.start_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.familiwallet.components.TransactionRow
import com.example.familiwallet.core.common.EXPENSES
import com.example.familiwallet.core.common.ShowScreen
import com.example.familiwallet.features.diagram.DiagramScreen
import com.example.familiwallet.features.start_screen.data.StartScreenViewState
import com.example.familiwallet.ui.theme.backgroundColor

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    forceLoad: MutableState<Boolean>,
    startViewModel: StartViewModel = hiltViewModel()
) {
    var viewState by remember { mutableStateOf(StartScreenViewState(emptyList(), emptyList())) }
    startViewModel.start = forceLoad.value
    Scaffold(
        modifier = modifier,
        backgroundColor = Color.White
    ) {
        LazyColumn {
            item {
                DiagramScreen(
                    modifier = Modifier
                        .padding(8.dp, 0.dp, 8.dp, 2.dp)
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 400.dp)
                        .background(backgroundColor, RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp)),
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

    ShowScreen(
        viewModel = startViewModel,
        forceLoad = forceLoad,
        onSuccess = {
            viewState = it as StartScreenViewState
        }
    )
}