package com.example.expenseobserver.features.start_screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.R
import com.example.expenseobserver.components.StartScreenWalletItems
import com.example.expenseobserver.components.TransactionRow
import com.example.expenseobserver.core.common.EXPENSES
import com.example.expenseobserver.core.common.ShowScreen
import com.example.expenseobserver.core.common.rippleClickable
import com.example.expenseobserver.features.diagram.DiagramScreen
import com.example.expenseobserver.features.start_screen.data.StartScreenViewState
import com.example.expenseobserver.features.timerange.TimeRangeDialog
import com.example.expenseobserver.navigation.Screen
import com.example.expenseobserver.ui.theme.backgroundColor
import com.example.expenseobserver.ui.theme.mainColor
import com.example.expenseobserver.ui.theme.textColor

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    update: MutableState<Boolean>,
    startViewModel: StartViewModel = hiltViewModel()
) {

    ShowScreen(
        viewModel = startViewModel,
        onSuccess = {
            UI(
                modifier = modifier,
                viewState = it as StartScreenViewState,
                startViewModel = startViewModel,
                navigation = navigation
            )
        }
    )

    LaunchedEffect(update.value) {
        startViewModel.getData()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun UI(
    modifier: Modifier,
    viewState: StartScreenViewState,
    startViewModel: StartViewModel,
    navigation: NavHostController,
) {
    val showTimeRangeDialog = remember { mutableStateOf(false) }

    if (showTimeRangeDialog.value) {
        TimeRangeDialog(
            dismissDialog = {
                showTimeRangeDialog.value = false
            },
            onButtonClick = {
                startViewModel.changeTimeRange(viewState.transactionsList)
                showTimeRangeDialog.value = false
            }
        )
    }

    Scaffold(
        modifier = modifier,
        backgroundColor = Color.White
    ) {
        LazyColumn {
            item {
                StartScreenWalletItems(
                    viewState = viewState,
                    onSettingsClick = {
                        Screen.WalletSettingsScreen.args = null
                        navigation.navigate(Screen.WalletSettingsScreen.route)
                                      },
                    onItemClick = { wallet ->
                        Screen.WalletScreen.args = Bundle().apply { putParcelable("wallet", wallet) }
                        navigation.navigate(Screen.WalletScreen.route)
                    }
                )
            }
            item { DiagramView(viewState = viewState, showTimeRangeDialog = showTimeRangeDialog) }
            transactionsItems(viewState = viewState)
        }
    }
}


@Composable
private fun DiagramView(
    viewState: StartScreenViewState,
    showTimeRangeDialog: MutableState<Boolean>
) {
    Spacer(modifier = Modifier.size(8.dp))
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (diagram, timeRangeButton) = createRefs()
        DiagramScreen(
            modifier = Modifier
                .constrainAs(diagram) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
                .defaultMinSize(minHeight = 400.dp),
            transactionsList = viewState.transactionsList,
            categoriesList = viewState.categoriesList
        )

        Box(
            modifier = Modifier
                .constrainAs(timeRangeButton) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .size(48.dp)
                .background(backgroundColor, RoundedCornerShape(10.dp))
                .rippleClickable { showTimeRangeDialog.value = true },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_time_range),
                contentDescription = "",
                tint = mainColor,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.transactionsItems(
    viewState: StartScreenViewState
) {
    viewState.summaryTransactionMap.forEach { (header, transactionList) ->
        stickyHeader {
            Box(
                modifier = Modifier
                    .background(backgroundColor)
                    .padding(8.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (header == EXPENSES) "Расходы" else "Доходы",
                    color = textColor,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .fillMaxWidth()
                )
            }
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