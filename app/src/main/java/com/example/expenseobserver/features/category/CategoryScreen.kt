package com.example.expenseobserver.features.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.expenseobserver.R
import com.example.expenseobserver.components.CategoryGridList
import com.example.expenseobserver.components.ThreeTabsLay
import com.example.expenseobserver.core.common.CategoryType
import com.example.expenseobserver.core.common.ShowScreen
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.features.dialog.ShowDeleteDialog
import com.example.expenseobserver.features.newcategory.data.NewCategoryModel
import com.example.expenseobserver.features.transacrionscreen.data.TransactionTabItem
import com.example.expenseobserver.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    forceLoad: MutableState<Boolean>,
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    var viewState by remember { mutableStateOf(CategoryScreenViewState(emptyList())) }
    val currentState = remember { mutableStateOf(0) }
    val showDeleteDialog = remember { mutableStateOf(false) }
    var deleteItem = UIModel.CategoryModel()

    ShowDeleteDialog(textResId = R.string.delete_description, openDialog = showDeleteDialog) {
        categoryViewModel.deleteItem(deleteItem)
    }

    Scaffold(
        modifier = modifier.padding(horizontal = 8.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(24.dp))
            ThreeTabsLay(tabList = tabList, currentState = currentState)
            Spacer(modifier = Modifier.size(24.dp))

            CategoryGridList(
                list = viewState.categoriesList,
                currentState = currentState,
                onItemClick = {
                    NewCategoryModel.setNewCategoryModel(
                        model = it,
                        isNewCategory = !viewState.categoriesList.contains(it),
                        categoryType = CategoryType.getCategory(currentState.value)
                    )
                    navigation.navigate(Screen.NewCategoryScreen.route)
                },
                onLongClick = {
                    deleteItem = it
                    if (deleteItem.id != null) {
                        showDeleteDialog.value = true
                    }
                })
        }
    }

    ShowScreen(
        viewModel = categoryViewModel,
        forceLoad = forceLoad,
        onSuccess = {
            viewState = it as CategoryScreenViewState
        }
    )

    LaunchedEffect(Unit) {
        categoryViewModel.getData()
    }
}

private val tabList = listOf(
    TransactionTabItem.Income,
    TransactionTabItem.Expense,
    TransactionTabItem.Bank
)