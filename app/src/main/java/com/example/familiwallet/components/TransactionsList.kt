package com.example.familiwallet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.familiwallet.core.common.EXPENSES
import com.example.familiwallet.core.common.INCOMES
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.ui.theme.expensesBackgroundColor
import com.example.familiwallet.ui.theme.expensesColor
import com.example.familiwallet.ui.theme.incomesBackgroundColor
import com.example.familiwallet.ui.theme.incomesColor

@Composable
fun TransactionsList(
    transactionList: List<UIModel.TransactionModel>
) {
    LazyColumn() {
        items(transactionList) { item ->
            TransactionRow(transaction = item)
        }
    }
}

@Composable
fun TransactionRow(
    transaction: UIModel.TransactionModel
) {
    Row(
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = if (transaction.type == INCOMES) incomesBackgroundColor else expensesBackgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .requiredHeight(80.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
       Icons.Outlined
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            text = transaction.value.toString(),
            color = if (transaction.type == INCOMES) incomesColor else expensesColor,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            text = transaction.category.orEmpty(),
            color = if (transaction.type == INCOMES) incomesColor else expensesColor,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionsListPreview() {
    TransactionsList(
        listOf(
            UIModel.TransactionModel(type = INCOMES, category = "Получка", value = 12.5),
            UIModel.TransactionModel(type = EXPENSES, category = "Мороженка", value = 10.5)
        )
    )
}


@Preview(showBackground = true)
@Composable
private fun TransactionRowPreview() {
    TransactionRow(
        UIModel.TransactionModel(
            type = INCOMES,
            category = "Получка",
            value = 12.5
        )
    )
}