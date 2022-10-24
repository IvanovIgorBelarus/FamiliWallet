package com.example.expenseobserver.features.start_screen.data

import com.example.expenseobserver.core.data.UIModel

class StartScreenViewState(
    var categoriesList: List<UIModel.CategoryModel>,
    var transactionsList: List<UIModel.TransactionModel>
)