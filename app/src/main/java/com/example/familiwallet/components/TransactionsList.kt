package com.example.familiwallet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.familiwallet.core.common.EXPENSES
import com.example.familiwallet.core.common.INCOMES
import com.example.familiwallet.core.data.AppIcons
import com.example.familiwallet.core.data.CategoryColor
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.utils.toStringFormat
import com.example.familiwallet.ui.theme.backgroundColor
import com.example.familiwallet.ui.theme.textColor

@Composable
fun TransactionsList(
    transactionList: List<UIModel.TransactionModel>
) {
    LazyColumn() {
        items(transactionList) { item ->
            TransactionRow(transaction = item, emptyList())
        }
    }
}

@Composable
fun TransactionRow(
    transaction: UIModel.TransactionModel,
    categoriesList: List<UIModel.CategoryModel>
) {
    Row(
        modifier = Modifier
            .padding(8.dp, 2.dp, 8.dp, 2.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(0.dp, 4.dp)
            .fillMaxWidth()
            .requiredHeight(80.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val itemCategory = categoriesList.firstOrNull { it.category == transaction.category }
        val iconRes = AppIcons.getImageRes(itemCategory?.icon)

        Spacer(modifier = Modifier.size(16.dp))
        Icon(
            painter = painterResource(id = iconRes.imageRes),
            contentDescription = "",
            tint = CategoryColor.getColor(itemCategory?.color.orEmpty()).color,
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column() {
            Text(
                text = transaction.category.toString(),
                color = textColor,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = transaction.date?.toStringFormat.orEmpty(),
                color = textColor,
                fontSize = 8.sp,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = transaction.value.toString(),
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionsListPreview() {
    TransactionsList(
        listOf(
            UIModel.TransactionModel(type = INCOMES, category = "Получка", date = 1647464400000, value = 12.5),
            UIModel.TransactionModel(type = EXPENSES, category = "Мороженка", date = 1647464400000, value = 10.5)
        )
    )
}


@Preview()
@Composable
private fun TransactionRowPreview() {
    TransactionRow(
        UIModel.TransactionModel(
            type = INCOMES,
            category = "Получка",
            date = 1647464400000,
            value = 12.5
        ),
        emptyList()
    )
}