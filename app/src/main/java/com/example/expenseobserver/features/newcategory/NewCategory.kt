package com.example.expenseobserver.features.newcategory

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.R
import com.example.expenseobserver.components.AmountTextField
import com.example.expenseobserver.components.CategoryIconGrid
import com.example.expenseobserver.components.CategoryRowWithoutText
import com.example.expenseobserver.components.MainButton
import com.example.expenseobserver.core.common.ShowScreen
import com.example.expenseobserver.core.common.rippleClickable
import com.example.expenseobserver.core.data.AppIcons
import com.example.expenseobserver.core.data.CategoryColor
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.features.newcategory.data.NewCategoryViewState
import com.example.expenseobserver.ui.theme.backgroundColor
import com.example.expenseobserver.ui.theme.bottomBarUnselectedContentColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewCategoryScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    forceLoad: MutableState<Boolean>,
    newCategoryViewModel: NewCategoryViewModel = hiltViewModel()
) {
    val resources = LocalContext.current.resources
    var viewState by remember { mutableStateOf(NewCategoryViewState(UIModel.CategoryModel())) }
    val categoryColor = remember { mutableStateOf(CategoryColor.UNKNOWN) }
    val icon = remember { mutableStateOf(AppIcons.UNKNOWN) }
    val categoryName = remember { mutableStateOf(viewState.category.category.orEmpty()) }
    val showError = remember { mutableStateOf(false) }

    ShowScreen(
        viewModel = newCategoryViewModel,
        forceLoad = forceLoad,
        onSuccess = {
            viewState = it as NewCategoryViewState
            categoryColor.value = CategoryColor.getColor(viewState.category.color.orEmpty())
            icon.value = AppIcons.getImageRes(viewState.category.icon.orEmpty())
            categoryName.value = viewState.category.category.orEmpty()
        }
    )

    Scaffold(
        modifier = modifier.padding(horizontal = 8.dp),
        backgroundColor = Color.White
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(24.dp))
            Box(
                modifier = Modifier
                    .background(backgroundColor, RoundedCornerShape(20.dp))
                    .size(150.dp),
                contentAlignment = Alignment.Center
            ) {
                CategoryRowWithoutText(categoryColor, icon)
            }
            Spacer(modifier = Modifier.size(24.dp))

            AmountTextField(
                stringValue = categoryName,
                placeHolderText = resources.getString(R.string.category_name_hint),
                modifier = Modifier.border(BorderStroke(1.dp, bottomBarUnselectedContentColor), RoundedCornerShape(10.dp)),
                showError = showError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                textBackgroundColor = Color.White
            )
            Spacer(modifier = Modifier.size(24.dp))

            Row(horizontalArrangement = Arrangement.Center) {
                MainButton(
                    modifier = Modifier.weight(1f),
                    text = R.string.cancel,
                    isSelected = mutableStateOf(false)
                ) {
                    navigation.popBackStack()
                }
                Spacer(modifier = Modifier.size(24.dp))
                MainButton(
                    modifier = Modifier.weight(1f),
                    text = R.string.done,
                    isSelected = mutableStateOf(true)
                ) {
                    newCategoryViewModel.sendCategoryRequest(
                        category = categoryName.value,
                        icon = icon.value,
                        color = categoryColor.value
                    ) {
                        forceLoad.value = !forceLoad.value
                        navigation.popBackStack()
                    }
                }
            }
            Spacer(modifier = Modifier.size(24.dp))

            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                item {
                    LazyHorizontalGrid(
                        rows = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.Center,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(96.dp)
                            .padding(4.dp)

                    ) {
                        items(newCategoryViewModel.getCategoriesColors()) { item ->
                            Box(modifier = Modifier
                                .padding(4.dp)
                                .size(36.dp)
                                .background(item.color, CircleShape)
                                .aspectRatio(1f)
                                .rippleClickable {
                                    categoryColor.value = CategoryColor.getColor(item.name)
                                })
                        }
                    }
                    Spacer(modifier = Modifier.size(24.dp))
                }

                newCategoryViewModel.getIcons().forEach { categoryItem ->
                    item {
                        CategoryIconGrid(item = categoryItem, icon)
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        newCategoryViewModel.getData()
    }
}