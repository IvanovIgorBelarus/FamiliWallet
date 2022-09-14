package com.example.familiwallet.features.transacrionscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.familiwallet.components.CategoryList
import com.example.familiwallet.components.CategoryTabs
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.ui.UiState
import com.example.familiwallet.features.dialog.ShowDialog
import com.example.familiwallet.features.loading.LoadingScreen
import com.example.familiwallet.features.transacrionscreen.data.TransactionTabItem
import com.example.familiwallet.ui.theme.backgroundColor

@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    transactionViewModel: TransactionViewModel = hiltViewModel()
) {
    val uiState by transactionViewModel.getUiState()
    val currentState = remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(12.dp)
                .background(backgroundColor)
        ) {
            CategoryTabs(tabList = tabList, currentState = currentState)
            Spacer(modifier = Modifier.size(24.dp))
            when (uiState) {
                is UiState.Success -> {
                    CategoryList(list = (uiState as UiState.Success<List<UIModel.CategoryModel>>).data)
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
    }
    LaunchedEffect(Unit){
        transactionViewModel.getCategories()
    }
}


private val tabList = listOf(
    TransactionTabItem.Income,
    TransactionTabItem.Expense,
    TransactionTabItem.Bank
)