package com.example.familiwallet.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.familiwallet.core.common.CategoryType
import com.example.familiwallet.core.common.EXPENSES
import com.example.familiwallet.core.common.noRippleClickable
import com.example.familiwallet.core.common.rippleClickable
import com.example.familiwallet.core.data.AppIcons
import com.example.familiwallet.core.data.CategoryColor
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.ui.theme.backgroundColor
import com.example.familiwallet.ui.theme.bottomBarUnselectedContentColor
import com.example.familiwallet.ui.theme.mainColor
import com.example.familiwallet.ui.theme.textColor

@Composable
fun CategoryRowList(
    list: List<UIModel.CategoryModel>,
    currentState: MutableState<CategoryType>,
    selectedCategory: MutableState<String>,
    showError: MutableState<Boolean>
) {
    val itemList = list.filter { it.type == currentState.value.type }

    Text(
        text = "Последние",
        color = mainColor,
        textAlign = TextAlign.Start,
        fontSize = 14.sp,
        modifier = Modifier.fillMaxWidth()
    )
    LazyRow(Modifier.padding(vertical = 4.dp)) {
        items(itemList) { item ->
            CategoryRow(
                category = item,
                selectedCategory = selectedCategory
            ) {
                selectedCategory.value = item.category.orEmpty()
                showError.value = false
            }
        }
    }
}

@Composable
fun CategoryGridList(
    list: List<UIModel.CategoryModel>,
    currentState: MutableState<CategoryType>,
    onItemClick: (UIModel.CategoryModel) -> Unit
) {
    val itemList = mutableListOf(
        UIModel.CategoryModel(
            icon = AppIcons.PLUS.name,
            color = CategoryColor.COLOR12.name
        )
    )
    val items = list.filter { it.type == currentState.value.type }
    itemList.addAll(items)
    LazyVerticalGrid(columns = GridCells.Adaptive(80.dp)) {
        items(itemList) { item ->
            CategoryRow(category = item, mutableStateOf("")) {
                onItemClick.invoke(item)
            }
        }
    }
}

@Composable
fun CategoryRow(
    category: UIModel.CategoryModel,
    selectedCategory: MutableState<String>,
    onItemClick: () -> Unit = {}
) {
    val iconColor = CategoryColor.getColor(category.color.orEmpty()).color
    val backgroundColor = if (selectedCategory.value == category.category) bottomBarUnselectedContentColor else backgroundColor
    Column(
        modifier = Modifier
            .padding(2.dp)
            .width(62.dp)
            .height(80.dp)
            .rippleClickable(color = Color.White) { onItemClick.invoke() }
            .background(backgroundColor, RoundedCornerShape(4.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .border(BorderStroke(4.dp, iconColor), CircleShape)
                .size(54.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = AppIcons.getImageRes(category.icon).imageRes),
                contentDescription = "",
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = category.category.orEmpty(),
            color = textColor,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CategoryRowWithoutText(
    color: MutableState<CategoryColor>,
    icon: MutableState<AppIcons>
) {
    Box(
        modifier = Modifier
            .border(BorderStroke(8.dp, color.value.color), CircleShape)
            .size(108.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = icon.value.imageRes),
            contentDescription = "",
            tint = color.value.color,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryListPreview() {
    CategoryRowList(
        listOf(
            UIModel.CategoryModel(
                category = "Топливо",
                type = EXPENSES,
                icon = "BEACH_ACCESS"
            ),
            UIModel.CategoryModel(
                category = "Топливо",
                type = EXPENSES,
                icon = "BUG_REPORT"
            )
        ),
        mutableStateOf(CategoryType.EXPENSE),
        mutableStateOf(""),
        mutableStateOf(false)
    )
}

@Preview(showBackground = true)
@Composable
private fun CategoryRowPreview() {
    CategoryRow(
        category = UIModel.CategoryModel(
            category = "Самый длинный текст",
            type = EXPENSES,
            icon = "BEACH_ACCESS"
        ),
        mutableStateOf(""),
        {}
    )
}