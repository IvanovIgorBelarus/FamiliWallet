package com.example.expenseobserver.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expenseobserver.core.common.TimeRangeType
import com.example.expenseobserver.features.transacrionscreen.data.TransactionTabItem
import com.example.expenseobserver.ui.theme.backgroundColor
import com.example.expenseobserver.ui.theme.buttonColor
import com.example.expenseobserver.ui.theme.textColor

@Composable
fun ThreeTabsLay(tabList: List<Any>, currentState: MutableState<Int>) {

    TabRow(
        modifier = Modifier
            .fillMaxWidth()

            .clip(RoundedCornerShape(10.dp))
            .border(BorderStroke(1.dp, buttonColor), RoundedCornerShape(10.dp)),
        selectedTabIndex = currentState.value,
        containerColor = buttonColor,
        indicator = { }
    ) {
        tabList.forEachIndexed { index, transactionTabItem ->
            val titleText = when (transactionTabItem) {
                is TransactionTabItem -> transactionTabItem.title
                is TimeRangeType -> transactionTabItem.text
                else -> ""
            }
            Tab(
                modifier = Modifier
                    .height(36.dp)
                    .padding(if (index == 1) 1.dp else 0.dp)
                    .background(
                        if (currentState.value == index) buttonColor else backgroundColor,
                        RoundedCornerShape(if (currentState.value == index) 10.dp else 0.dp)
                    ),
                selected = currentState.value == index,
                onClick = { currentState.value = index },
                text = {
                    Text(
                        text = titleText,
                        color = if (currentState.value == index) Color.White else textColor,
                        fontSize = 14.sp
                    )
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFF19BF3)
private fun TabsPreview() {
    ThreeTabsLay(
        tabList = listOf(TransactionTabItem.Income, TransactionTabItem.Expense, TransactionTabItem.Bank),
        currentState = mutableStateOf(0)
    )
}