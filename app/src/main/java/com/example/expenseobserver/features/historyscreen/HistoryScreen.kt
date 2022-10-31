package com.example.expenseobserver.features.historyscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.components.TransactionRow
import com.example.expenseobserver.core.common.ShowScreen
import com.example.expenseobserver.core.utils.toStringDayFormat
import com.example.expenseobserver.features.historyscreen.data.HistoryViewState
import com.example.expenseobserver.ui.theme.textColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController? = null,
    forceLoad: MutableState<Boolean>,
    historyViewModel: HistoryViewModel = hiltViewModel()
) {
    var viewState by remember { mutableStateOf(HistoryViewState(emptyList(), emptyMap())) }

    Scaffold(
        modifier = modifier,
        backgroundColor = Color.White
    ) {
        LazyColumn() {
            viewState.transactionsGroupList.forEach { (date, transactionList) ->
                stickyHeader {
                    Text(
                        text = date?.toStringDayFormat.orEmpty(),
                        color = textColor,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier
                            .padding(0.dp, 24.dp, 12.dp, 0.dp)
                            .background(Color.White)
                            .fillMaxWidth()
                            .height(24.dp)

                    )
                }
                items(items = transactionList,
                    itemContent = { transaction ->
                        TransactionRow(
                            transaction = transaction,
                            categoriesList = viewState.categoriesList
                        ) { item ->
                            //onItemClick
                        }
                    })
            }
        }
    }

    ShowScreen(
        viewModel = historyViewModel,
        forceLoad = forceLoad,
        onSuccess = {
            viewState = it as HistoryViewState
        }
    )

    LaunchedEffect(Unit) {
        historyViewModel.getData()
    }
}