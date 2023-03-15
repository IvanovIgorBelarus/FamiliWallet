package com.example.expenseobserver.features.start_screen.data

import com.example.expenseobserver.core.data.UIModel

class StartScreenViewState(
    var categoriesList: List<UIModel.CategoryModel>,
    var transactionsList: List<UIModel.TransactionModel>,
    var summaryTransactionMap: Map<String?, List<UIModel.TransactionModel>>,
    var walletList: List<UIModel.WalletModel> = listOf(
        UIModel.WalletModel(value = 10.00, currency = "BYN", name = "Копилка"),
        UIModel.WalletModel(value = 10000.35, currency = "EUR", name = "Заначка"),
        UIModel.WalletModel(value = 1200.00, currency = "BYN", name = "Платилка"),
        UIModel.WalletModel(value = 1110.11, currency = "RUB", name = "Отдых"),
        UIModel.WalletModel(value = 10000000.00, currency = "USD", name = "Банк"),
    )
)