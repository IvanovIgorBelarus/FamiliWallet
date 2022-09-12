package com.example.familiwallet.features.transacrionscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.familiwallet.features.transacrionscreen.data.TransactionTabItem
import com.example.familiwallet.ui.theme.backgroundColor
import com.example.familiwallet.ui.theme.buttonColor
import com.example.familiwallet.ui.theme.textColor

@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    transactionViewModel: TransactionViewModel = hiltViewModel()
) {
    val currentState = remember { mutableStateOf(0) }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Tabs(currentState = currentState)
        }
    }
}

@Composable
private fun Tabs(currentState: MutableState<Int>) {
        TabRow(
            selectedTabIndex = currentState.value,
            backgroundColor = backgroundColor,
            indicator = { tabPositions ->

            }
        ) {
            tabList.forEachIndexed { index, transactionTabItem ->
                Tab(
                    modifier = Modifier
                        .padding(2.dp)
                        .background(
                            if (currentState.value == index) buttonColor else backgroundColor,
                            RoundedCornerShape(20.dp)
                        ),
                    selected = currentState.value == index,
                    onClick = { currentState.value = index },
                    text = { Text(text = transactionTabItem.title, color = if (currentState.value == index) backgroundColor else textColor) }
                )
            }
        }
}

@Composable
@Preview(showBackground = true)
private fun TabsPreview() {
    Tabs(currentState = mutableStateOf(0))
}

private val tabList = listOf(
    TransactionTabItem.Income,
    TransactionTabItem.Expense,
    TransactionTabItem.Bank
)