package com.example.familiwallet.features.start_screen

import com.example.familiwallet.core.data.UIModel

class StartScreenViewState(
    var categoriesList: List<UIModel.CategoryModel>,
    var transactionsList: List<UIModel.TransactionModel>
)