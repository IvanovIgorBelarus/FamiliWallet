package com.example.expenseobserver.features.transacrionscreen.data

sealed class TransactionTabItem(val title: String, id: Int) {
    object Income : TransactionTabItem("Доходы", 0)
    object Expense : TransactionTabItem("Расходы", 1)
}