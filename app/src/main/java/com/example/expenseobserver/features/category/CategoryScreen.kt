package com.example.expenseobserver.features.category

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.R
import com.example.components.CategoryGridList
import com.example.components.ThreeTabsLay
import com.example.expenseobserver.core.common.ShowScreen
import com.example.expenseobserver.features.dialog.ShowDeleteDialog
import com.example.expenseobserver.features.newcategory.data.NewCategoryModel
import com.example.data.TransactionTabItem
import com.example.navigation.Screen


@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    currentState: MutableState<Int>,
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    ShowScreen(
        viewModel = categoryViewModel,
        onSuccess = {
            UI(
                modifier = modifier,
                viewState = it as CategoryScreenViewState,
                categoryViewModel = categoryViewModel,
                navigation = navigation,
                currentState = currentState
            )
        }
    )

    LaunchedEffect(Unit) {
        categoryViewModel.getData()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UI(
    modifier: Modifier,
    viewState: CategoryScreenViewState,
    categoryViewModel: CategoryViewModel,
    navigation: NavHostController,
    currentState: MutableState<Int>
) {
    val showDeleteDialog = remember { mutableStateOf(false) }
    var deleteItem = com.example.data.UIModel.CategoryModel()

    ShowDeleteDialog(
        titleResId = R.string.delete_category_title,
        textResId = R.string.delete_description,
        openDialog = showDeleteDialog
    ) {
        categoryViewModel.deleteItem(deleteItem)
    }

    Surface(
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
                        categoryType = com.example.common.CategoryType.getCategory(currentState.value)
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
}

private val tabList = listOf(
    TransactionTabItem.Expense,
    TransactionTabItem.Income
)