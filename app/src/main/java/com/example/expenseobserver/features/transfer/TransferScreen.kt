package com.example.expenseobserver.features.transfer

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.R
import com.example.components.AmountTextField
import com.example.components.ButtonsLay
import com.example.components.HorizontalPagerWithIndicator
import com.example.components.WalletTemplate
import com.example.expenseobserver.core.common.ShowScreen
import com.example.common.formatAmount
import com.example.expenseobserver.features.transfer.data.TransferScreenViewState
import com.example.data.theme.bottomBarUnselectedContentColor
import com.example.data.theme.textColor
import com.example.navigation.Screen

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
        val id = Screen.TransferScreen.args?.getString("ID", "empty")
        transferViewModel.getTransferInfo(id)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun UI(
    modifier: Modifier,
    viewState: TransferScreenViewState,
    navigation: NavHostController,
    transferViewModel: TransferViewModel
) {
    val resources = LocalContext.current.resources
    val pageState = rememberPagerState(0)
    val currentItemTo = remember { mutableStateOf(viewState.walletsTo[0]) }
    val isExchangeFieldVisible = remember { mutableStateOf(viewState.walletFrom.currency != currentItemTo.value.currency) }

    val value = remember { mutableStateOf("") }
    val showValueError = remember { mutableStateOf(false) }

    val exchangeRateValue = remember { mutableStateOf("") }
    val showExchangeRateError = remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.padding(horizontal = 8.dp),
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    WalletTemplate(viewState.walletFrom)
                }

                item {
                    HorizontalPagerWithIndicator(
                        state = pageState,
                        pageCount = viewState.walletsTo.size
                    ) { index ->
                        val currentItem = viewState.walletsTo[index]
                        WalletTemplate(currentItem)
                    }

                }

                item {
                    Spacer(modifier = Modifier.size(12.dp))
                    AmountTextField(
                        stringValue = value,
                        placeHolderText = resources.getString(R.string.transfer_amount),
                        modifier = Modifier.border(BorderStroke(1.dp, bottomBarUnselectedContentColor), RoundedCornerShape(10.dp)),
                        showError = showValueError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textBackgroundColor = Color.White
                    )
                }

                if (isExchangeFieldVisible.value) {
                    item {
                        Spacer(modifier = Modifier.size(24.dp))
                        AmountTextField(
                            stringValue = exchangeRateValue,
                            placeHolderText = resources.getString(R.string.transfer_currency_hint),
                            modifier = Modifier.border(BorderStroke(1.dp, bottomBarUnselectedContentColor), RoundedCornerShape(10.dp)),
                            showError = showExchangeRateError,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textBackgroundColor = Color.White
                        )
                    }
                }

                if (value.value.toDoubleOrNull() != null && exchangeRateValue.value.toDoubleOrNull() != null) {
                    item {
                        Spacer(modifier = Modifier.size(24.dp))
                        var exchangeAmount = value.value.toDouble() * exchangeRateValue.value.toDouble()
                        Text(
                            text = "${
                                value.value.toDouble().formatAmount()
                            } ${viewState.walletFrom.currency} меняем на ${exchangeAmount.formatAmount()} ${currentItemTo.value.currency} по курсу ${exchangeRateValue.value}",
                            color = textColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .weight(1f)
            )
            Spacer(modifier = Modifier.size(24.dp))
            ButtonsLay(
                onCancelClick = { navigation.popBackStack() },
                onConfirmClick = {
                    transferViewModel.startTransfer(
                        sourceId = viewState.walletFrom.id,
                        targetId = currentItemTo.value.id,
                        amount = value.value.toDoubleOrNull(),
                        exchangeRate = exchangeRateValue.value.toDoubleOrNull()
                    ) {
                        navigation.popBackStack()
                    }
                }
            )
            Spacer(modifier = Modifier.size(24.dp))
        }
    }

    LaunchedEffect(pageState) {
        snapshotFlow { pageState.currentPage }.collect { page ->
            currentItemTo.value = viewState.walletsTo[page]
            isExchangeFieldVisible.value = viewState.walletFrom.currency != currentItemTo.value.currency
            exchangeRateValue.value = ""
        }
    }
}
