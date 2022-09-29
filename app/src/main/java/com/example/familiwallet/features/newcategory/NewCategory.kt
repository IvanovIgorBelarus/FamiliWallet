package com.example.familiwallet.features.newcategory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.example.familiwallet.R
import com.example.familiwallet.components.AmountTextField
import com.example.familiwallet.components.CategoryIconGrid
import com.example.familiwallet.components.CategoryRowWithoutText
import com.example.familiwallet.components.TransactionButton
import com.example.familiwallet.core.common.ShowScreen
import com.example.familiwallet.core.common.noRippleClickable
import com.example.familiwallet.core.common.rippleClickable
import com.example.familiwallet.core.data.AppIcons
import com.example.familiwallet.core.data.CategoryColor
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.features.newcategory.data.NewCategoryViewState
import com.example.familiwallet.ui.theme.backgroundColor
import com.example.familiwallet.ui.theme.bottomBarUnselectedContentColor

@Composable
fun NewCategoryScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    newCategoryViewModel: NewCategoryViewModel = hiltViewModel()
) {
    newCategoryViewModel.getData()

    val resources = LocalContext.current.resources
    var viewState by remember { mutableStateOf(NewCategoryViewState(UIModel.CategoryModel())) }
    val categoryColor = remember { mutableStateOf(CategoryColor.getColor(viewState.category.color.orEmpty()).color) }
    val icon = remember { mutableStateOf(AppIcons.UNKNOWN) }
    val categoryName = remember { mutableStateOf(viewState.category.category.orEmpty()) }
    val showError = remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.padding(horizontal = 8.dp),
        backgroundColor = Color.White
    ) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {
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
            }

            item {
                AmountTextField(
                    stringValue = categoryName,
                    placeHolderText = resources.getString(R.string.category_name_hint),
                    modifier = Modifier.border(BorderStroke(1.dp, bottomBarUnselectedContentColor), RoundedCornerShape(10.dp)),
                    showError = showError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    textBackgroundColor = Color.White
                )
                Spacer(modifier = Modifier.size(24.dp))
            }

            item {
                Row(horizontalArrangement = Arrangement.Center) {
                    TransactionButton(
                        modifier = Modifier.weight(1f),
                        text = R.string.cancel,
                        isSelected = mutableStateOf(false)
                    ) {
                        navigation.popBackStack()
                    }
                    Spacer(modifier = Modifier.size(24.dp))
                    TransactionButton(
                        modifier = Modifier.weight(1f),
                        text = R.string.done,
                        isSelected = mutableStateOf(true)
                    ) {
                        //внести изменения в категорию
                    }
                }
                Spacer(modifier = Modifier.size(24.dp))
            }

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
                                categoryColor.value = CategoryColor.getColor(item.name).color
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

    ShowScreen(
        viewModel = newCategoryViewModel,
        forceLoad = mutableStateOf(true),
        onSuccess = { viewState = it as NewCategoryViewState }
    )
}