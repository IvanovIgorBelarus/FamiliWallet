package com.example.familiwallet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.familiwallet.core.common.CategoryType
import com.example.familiwallet.features.main.data.Transaction
import com.example.familiwallet.ui.theme.expensesBackgroundColor
import com.example.familiwallet.ui.theme.expensesColor
import com.example.familiwallet.ui.theme.incomesBackgroundColor
import com.example.familiwallet.ui.theme.incomesColor

@Composable
fun TransactionsList(
    modifier: Modifier
) {
    LazyColumn {
        items(transactions) { item ->
            TransactionRow(transaction = item)
        }
    }
}

@Composable
fun TransactionRow(
    transaction: Transaction
) {
    Row(
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = if (transaction.type == CategoryType.INCOMES) incomesBackgroundColor else expensesBackgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(start= 10.dp),
            text = transaction.value,
            color = if (transaction.type == CategoryType.INCOMES) incomesColor else expensesColor,
            fontSize = 18.sp,
            textAlign = TextAlign.Center

        )
        Text(
            modifier = Modifier.weight(1f).padding(10.dp),
            text = transaction.category,
            color = if (transaction.type == CategoryType.INCOMES) incomesColor else expensesColor,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}


val transactions = listOf(
    Transaction(CategoryType.INCOMES, "100", "Еда"),
    Transaction(CategoryType.EXPENSES, "-100", "Еда"),
    Transaction(CategoryType.INCOMES, "100", "Еда"),
    Transaction(CategoryType.EXPENSES, "-100", "Еда"),
    Transaction(CategoryType.INCOMES, "100", "Еда"),
    Transaction(CategoryType.INCOMES, "100", "Еда"),
    Transaction(CategoryType.EXPENSES, "-100", "Еда"),
    Transaction(CategoryType.INCOMES, "100", "Еда"),
    Transaction(CategoryType.EXPENSES, "-100", "Еда"),
    Transaction(CategoryType.INCOMES, "100", "Еда"),
    Transaction(CategoryType.EXPENSES, "-100", "Еда"),
    Transaction(CategoryType.INCOMES, "100", "Еда")
)