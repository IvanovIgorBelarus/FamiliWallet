package com.example.familiwallet.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.familiwallet.core.common.EXPENSES
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.utils.AppIcons
import com.example.familiwallet.ui.theme.diagramColor4
import com.example.familiwallet.ui.theme.mainColor
import com.example.familiwallet.ui.theme.textColor

@Composable
fun CategoryList(list: List<UIModel.CategoryModel>) {
    LazyRow {
        items(list) { item ->
            CategoryRow(category = item)
        }
    }
}

@Composable
private fun CategoryRow(
    category: UIModel.CategoryModel
) {
    val iconColor = if (category.color.isNullOrEmpty()) mainColor else Color(category.color!!.toLong())
    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .border(BorderStroke(5.dp, iconColor), CircleShape)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = AppIcons.getImageRes(category.icon).imageRes),
                contentDescription = "",
                tint = iconColor,
                modifier = Modifier.size(54.dp)
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = category.category.orEmpty(),
            color = textColor,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryListPreview() {
    CategoryList(
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
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun CategoryRowPreview() {
    CategoryRow(
        category = UIModel.CategoryModel(
            category = "Топливо",
            type = EXPENSES,
            icon = "BEACH_ACCESS"
        )
    )
}