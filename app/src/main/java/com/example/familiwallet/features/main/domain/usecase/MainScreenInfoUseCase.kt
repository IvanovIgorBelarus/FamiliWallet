package com.example.familiwallet.features.main.domain.usecase

import com.example.familiwallet.core.data.UIModel

interface MainScreenInfoUseCase {

    suspend fun getExpensesCategoriesList(): List<UIModel.CategoryModel>

    suspend fun getIncomesCategoriesList(): List<UIModel.CategoryModel>

    suspend fun getTransactionsList(): List<UIModel.TransactionModel>
}