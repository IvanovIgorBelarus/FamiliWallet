package com.example.familiwallet.features.main.domain.usecase

import com.example.familiwallet.core.common.EXPENSES
import com.example.familiwallet.core.common.INCOMES
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.repository.DataRepository
import javax.inject.Inject

class MainScreenInfoUseCaseImpl @Inject constructor(
    private val repo: DataRepository
) : MainScreenInfoUseCase {
    override suspend fun getExpensesCategoriesList(): List<UIModel.CategoryModel> =
        repo.getCategoriesList().filter { it.type == EXPENSES }


    override suspend fun getIncomesCategoriesList(): List<UIModel.CategoryModel> =
        repo.getCategoriesList().filter { it.type == INCOMES }

    override suspend fun getTransactionsList(): List<UIModel.TransactionModel> =
        repo.getTransactionsList()
}