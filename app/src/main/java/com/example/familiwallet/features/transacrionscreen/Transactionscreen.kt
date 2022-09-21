package com.example.familiwallet.features.transacrionscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.familiwallet.R
import com.example.familiwallet.components.CategoryList
import com.example.familiwallet.components.CategoryTabs
import com.example.familiwallet.components.TransactionButton
import com.example.familiwallet.core.common.CashType
import com.example.familiwallet.core.common.CategoryType
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.ui.UiState
import com.example.familiwallet.features.dialog.ShowDialog
import com.example.familiwallet.features.loading.LoadingScreen
import com.example.familiwallet.features.transacrionscreen.data.TransactionTabItem
import com.example.familiwallet.ui.theme.backgroundColor
import com.example.familiwallet.ui.theme.buttonColor
import com.example.familiwallet.ui.theme.textColor

@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    transactionViewModel: TransactionViewModel = hiltViewModel()
) {
    val uiState by transactionViewModel.getUiState()
    val currentState = remember { mutableStateOf(CategoryType.INCOME) }
    val selectedCategory = remember { mutableStateOf("") }
    val cashType = remember { mutableStateOf(CashType.CARDS) }
    var amount by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier
    ) {
        when (uiState) {
            is UiState.Success -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(12.dp)
                ) {
                    CategoryTabs(tabList = tabList, currentState = currentState)
                    Spacer(modifier = Modifier.size(24.dp))

                    CategoryList(
                        list = (uiState as UiState.Success<List<UIModel.CategoryModel>>).data,
                        currentState,
                        selectedCategory
                    )

                    Row(horizontalArrangement = Arrangement.Center) {
                        TransactionButton(
                            modifier = Modifier.weight(1f),
                            text = R.string.card,
                            isSelected = mutableStateOf(cashType.value == CashType.CARDS)
                        ) { cashType.value = CashType.CARDS }
                        Spacer(modifier = Modifier.size(8.dp))
                        TransactionButton(
                            modifier = Modifier.weight(1f),
                            text = R.string.cash,
                            isSelected = mutableStateOf(cashType.value == CashType.CASHES)
                        ) { cashType.value = CashType.CASHES }
                    }
                    Spacer(modifier = Modifier.size(24.dp))

                    TextField(
                        value = amount,
                        onValueChange = { value -> amount = value },
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(36.dp)
                            .border(BorderStroke(1.dp, buttonColor), RoundedCornerShape(10.dp)),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(color = textColor, fontSize = 14.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = backgroundColor,
//                            cursorColor = backgroundColor,
                            focusedIndicatorColor = backgroundColor,
                            unfocusedIndicatorColor = backgroundColor
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    TransactionButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = R.string.cash,
                        isSelected = mutableStateOf(true)
                    ) { }
                    Spacer(modifier = Modifier.size(12.dp))
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
    LaunchedEffect(Unit) {
        transactionViewModel.getCategories()
    }
}


private val tabList = listOf(
    TransactionTabItem.Income,
    TransactionTabItem.Expense,
    TransactionTabItem.Bank
)