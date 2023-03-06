package com.example.expenseobserver.features.start_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.R
import com.example.expenseobserver.components.TransactionRow
import com.example.expenseobserver.core.common.ShowScreen
import com.example.expenseobserver.core.common.rippleClickable
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UiState
import com.example.expenseobserver.features.diagram.DiagramScreen
import com.example.expenseobserver.features.dialog.ShowDeleteDialog
import com.example.expenseobserver.features.start_screen.data.StartScreenViewState
import com.example.expenseobserver.features.timerange.TimeRangeDialog
import com.example.expenseobserver.ui.theme.backgroundColor
import com.example.expenseobserver.ui.theme.mainColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    forceLoad: MutableState<Boolean>,
    startViewModel: StartViewModel = hiltViewModel()
) {
    startViewModel.start = forceLoad.value

    ShowScreen(
        viewModel = startViewModel,
        forceLoad = forceLoad,
        onSuccess = {
            UI(modifier = modifier, viewState = it as StartScreenViewState, startViewModel = startViewModel)
        }
    )

    LaunchedEffect(forceLoad) {
        startViewModel.getData()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UI (
    modifier: Modifier,
    viewState: StartScreenViewState,
    startViewModel: StartViewModel
){
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
                                top.linkTo(parent.top, margin = 10.dp)
                                end.linkTo(parent.end, margin = 24.dp)
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
            items(viewState.transactionsList) { item ->
                TransactionRow(
                    transaction = item,
                    categoriesList = viewState.categoriesList
                ) { item ->
                    deleteItem = item
                    showDeleteDialog.value = true
                }
            }
        }
    }
}