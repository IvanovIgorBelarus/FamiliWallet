package com.example.expenseobserver.features.historyscreen.data

import com.example.expenseobserver.core.data.UIModel

class HistoryViewState(
    var categoriesList: List<UIModel.CategoryModel>,
    var transactionsGroupList: Map<Long,List<UIModel.TransactionModel>>,
    var summaryTransactionList: List<UIModel.TransactionModel>
)