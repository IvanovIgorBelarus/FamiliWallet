package com.example.expenseobserver.features.walletscreen.data

import com.example.expenseobserver.core.data.UIModel

class WalletScreenViewState(
    var walletList: List<UIModel.WalletModel>,
    val operationsList: List<UIModel>,
    val categoriesList: List<UIModel.CategoryModel>
)