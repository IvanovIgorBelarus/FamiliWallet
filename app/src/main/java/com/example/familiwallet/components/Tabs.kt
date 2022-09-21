package com.example.familiwallet.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.familiwallet.core.common.BANK
import com.example.familiwallet.core.common.CategoryType
import com.example.familiwallet.core.common.EXPENSES
import com.example.familiwallet.core.common.INCOMES
import com.example.familiwallet.features.transacrionscreen.data.TransactionTabItem
import com.example.familiwallet.ui.theme.backgroundColor
import com.example.familiwallet.ui.theme.buttonColor
import com.example.familiwallet.ui.theme.textColor

@Composable
fun CategoryTabs(tabList: List<TransactionTabItem>, currentState: MutableState<CategoryType>) {
    TabRow(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(36.dp)
            .clip(RoundedCornerShape(10.dp)),
        selectedTabIndex = currentState.value.position,
        backgroundColor = buttonColor,
        indicator = { tabPositions ->

        }
    ) {
        tabList.forEachIndexed { index, transactionTabItem ->
            Tab(
                modifier = Modifier
                    .border(
                        BorderStroke(1.dp, buttonColor),
                        when (index) {
                            0 -> RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp)
                            1 -> RoundedCornerShape(0.dp)
                            2 -> RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp)
                            else -> RoundedCornerShape(0.dp)
                        }
                    )
                    .background(
                        if (currentState.value == CategoryType.getCategory(index)) buttonColor else backgroundColor,
                        RoundedCornerShape(if (currentState.value.position == index) 10.dp else 0.dp)
                    ),
                selected = currentState.value.position == index,
                onClick = { currentState.value = CategoryType.getCategory(index) },
                text = { Text(
                    text = transactionTabItem.title,
                    color = if (currentState.value.position == index) Color.White else textColor,
                    fontSize = 14.sp
                ) }
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFF19BF3)
private fun TabsPreview() {
    CategoryTabs(
        tabList = listOf(TransactionTabItem.Income, TransactionTabItem.Expense, TransactionTabItem.Bank),
        currentState = mutableStateOf(CategoryType.EXPENSE)
    )
}