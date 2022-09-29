package com.example.familiwallet.features.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.familiwallet.components.CategoryGridList
import com.example.familiwallet.components.CategoryTabs
import com.example.familiwallet.components.findStartDestination
import com.example.familiwallet.core.common.CategoryType
import com.example.familiwallet.core.common.ShowScreen
import com.example.familiwallet.features.newcategory.data.NewCategoryModel
import com.example.familiwallet.features.transacrionscreen.data.TransactionTabItem
import com.example.familiwallet.navigation.Screen

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    forceLoad: MutableState<Boolean>,
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    var viewState by remember { mutableStateOf(CategoryScreenViewState(emptyList())) }
    val currentState = remember { mutableStateOf(CategoryType.INCOME) }

    Scaffold(
        modifier = modifier.padding(horizontal = 8.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CategoryTabs(tabList = tabList, currentState = currentState)
            Spacer(modifier = Modifier.size(24.dp))

            CategoryGridList(list = viewState.categoriesList, currentState = currentState) {
                NewCategoryModel.setNewCategoryModel(it)
                navigation.navigate(Screen.NewCategoryScreen.route)
            }
        }
    }

    ShowScreen(viewModel = categoryViewModel,
        forceLoad = forceLoad,
        onSuccess = {
            viewState = it as CategoryScreenViewState
        }
    )
}

private val tabList = listOf(
    TransactionTabItem.Income,
    TransactionTabItem.Expense,
    TransactionTabItem.Bank
)