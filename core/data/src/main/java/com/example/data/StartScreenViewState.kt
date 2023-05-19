package com.example.data

import com.example.data.UIModel

class StartScreenViewState(
    var categoriesList: List<com.example.data.UIModel.CategoryModel>,
    var transactionsList: List<com.example.data.UIModel.TransactionModel>,
    var summaryTransactionMap: Map<String?, List<com.example.data.UIModel.TransactionModel>>,
    var walletList: List<com.example.data.UIModel.WalletModel> = listOf(
        com.example.data.UIModel.WalletModel(value = 10.00, currency = "BYN", name = "Копилка"),
        com.example.data.UIModel.WalletModel(value = 10000.35, currency = "EUR", name = "Заначка"),
        com.example.data.UIModel.WalletModel(value = 1200.00, currency = "BYN", name = "Платилка"),
        com.example.data.UIModel.WalletModel(value = 1110.11, currency = "RUB", name = "Отдых"),
        com.example.data.UIModel.WalletModel(value = 10000000.00, currency = "USD", name = "Банк"),
    )
)