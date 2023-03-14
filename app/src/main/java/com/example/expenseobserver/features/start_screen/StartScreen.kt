package com.example.expenseobserver.features.start_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.expenseobserver.components.TransactionRow
import com.example.expenseobserver.core.common.EXPENSES
import com.example.expenseobserver.core.common.ShowScreen
import com.example.expenseobserver.core.common.rippleClickable
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.features.diagram.DiagramScreen
import com.example.expenseobserver.features.dialog.ShowDeleteDialog
import com.example.expenseobserver.features.start_screen.data.StartScreenViewState
import com.example.expenseobserver.features.timerange.TimeRangeDialog
import com.example.expenseobserver.ui.theme.backgroundColor
import com.example.expenseobserver.ui.theme.expensesBackgroundColor
import com.example.expenseobserver.ui.theme.incomesBackgroundColor
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
                startViewModel = startViewModel
            )
        }
    )

    LaunchedEffect(update.value) {
        startViewModel.getData()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UI(
    modifier: Modifier,
    viewState: StartScreenViewState,
    startViewModel: StartViewModel
) {
    val showDeleteDialog = remember { mutableStateOf(false) }
    val showTimeRangeDialog = remember { mutableStateOf(false) }
    var deleteItem = UIModel.TransactionModel()

    ShowDeleteDialog(textResId = R.string.delete_description, openDialog = showDeleteDialog) {
        startViewModel.deleteItem(deleteItem)
    }

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
                        categoriesList = viewState.categoriesList,
//                        uiState = viewState
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

            viewState.summaryTransactionMap.forEach { (header, transactionList) ->
                stickyHeader {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .background(
                                color = if (header == EXPENSES) expensesBackgroundColor else incomesBackgroundColor,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .fillMaxWidth()
                            .requiredHeight(80.dp),
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
                                .height(24.dp)

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
    }
}