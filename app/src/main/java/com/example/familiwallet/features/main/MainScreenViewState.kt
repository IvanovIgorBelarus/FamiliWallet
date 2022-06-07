package com.example.familiwallet.features.main

import com.example.familiwallet.core.data.UIModel

class MainScreenViewState(
    var expensesList: List<UIModel.CategoryModel>,
    var incomesList: List<UIModel.CategoryModel>
)