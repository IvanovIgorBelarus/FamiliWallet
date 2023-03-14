package com.example.expenseobserver.features.start_screen.data

import com.example.expenseobserver.core.data.UIModel

class StartScreenViewState(
    var categoriesList: List<UIModel.CategoryModel>,
    var transactionsList: List<UIModel.TransactionModel>,
    var summaryTransactionMap: Map<String?, List<UIModel.TransactionModel>>,
    var walletList: List<UIModel.WalletModel> = listOf(
        UIModel.WalletModel(amount = "10.00", currency = "BYN", name = "Копилка"),
        UIModel.WalletModel(amount = "10 000.35", currency = "EUR", name = "Заначка"),
        UIModel.WalletModel(amount = "1200.00", currency = "BYN", name = "Платилка"),
        UIModel.WalletModel(amount = "1110.11", currency = "RUB", name = "Отдых"),
        UIModel.WalletModel(amount = "10 000 000.00", currency = "USD", name = "Банк"),
    )
)