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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.R
import com.example.components.AmountTextField
import com.example.components.CategoryIconGrid
import com.example.components.CategoryRowWithoutText
import com.example.components.ColorsView
import com.example.components.MainButton
import com.example.components.ShowScreen
import com.example.data.AppIcons
import com.example.data.CategoryColor
import com.example.expenseobserver.features.newcategory.data.NewCategoryViewState
import com.example.data.theme.backgroundColor
import com.example.data.theme.bottomBarUnselectedContentColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewCategoryScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    newCategoryViewModel: NewCategoryViewModel = hiltViewModel()
) {

    ShowScreen(
        viewModel = newCategoryViewModel,
        onSuccess = {
            UI(
                modifier = modifier,
                viewState = it as NewCategoryViewState,
                navigation = navigation,
//                update = update,
                newCategoryViewModel = newCategoryViewModel
            )
        }
    )

    LaunchedEffect(Unit) {
        newCategoryViewModel.getData()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun UI(
    modifier: Modifier,
    viewState: NewCategoryViewState,
    navigation: NavHostController,
    newCategoryViewModel: NewCategoryViewModel
) {
    val resources = LocalContext.current.resources
    val categoryColor = remember { mutableStateOf( viewState.category.color?: CategoryColor.COLOR0.name) }
    val icon = remember { mutableStateOf(AppIcons.getImageRes(viewState.category.icon.orEmpty())) }
    val categoryName = remember { mutableStateOf(viewState.category.category.orEmpty()) }
    val showError = remember { mutableStateOf(false) }

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
                CategoryRowWithoutText(mutableStateOf(CategoryColor.getColor(categoryColor.value)), icon)
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
                        navigation.popBackStack()
                    }
                }
            }
            Spacer(modifier = Modifier.size(24.dp))

            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                item {
                    ColorsView(
                        colorName = categoryColor,
                        colorList = newCategoryViewModel.getCategoriesColors()
                    )
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
}