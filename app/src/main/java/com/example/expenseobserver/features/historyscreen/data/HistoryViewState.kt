package com.example.expenseobserver.features.historyscreen.data

import com.example.data.UIModel

class HistoryViewState(
    var categoriesList: List<UIModel.CategoryModel>,
    var transactionsGroupList: Map<Long,List<UIModel.TransactionModel>>
)