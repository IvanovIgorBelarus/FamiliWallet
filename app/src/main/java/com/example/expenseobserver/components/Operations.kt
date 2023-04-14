package com.example.expenseobserver.components

import androidx.compose.runtime.Composable
import com.example.expenseobserver.core.data.UIModel

@Composable
fun OperationView(
    item: UIModel,
    categoriesList: List<UIModel.CategoryModel>,
    onItemClick: (UIModel) -> Unit = {}
) {
    when (item) {
        is UIModel.TransactionModel -> TransactionRow(
            transaction = item,
            categoriesList = categoriesList,
            onClick = onItemClick
        )
        else ->{}
    }
}