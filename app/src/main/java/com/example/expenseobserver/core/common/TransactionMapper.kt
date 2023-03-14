package com.example.expenseobserver.core.common

import com.example.expenseobserver.core.data.UIModel

object TransactionMapper {
    fun mapSummaryTransactionList(
        categoriesList: List<UIModel.CategoryModel>,
        transactionsList: List<UIModel.TransactionModel>
    ): List<UIModel.TransactionModel> {
        var resultList = mutableListOf<UIModel.TransactionModel>()
        categoriesList.forEach { categoryItem ->
            var sum = 0.00
            transactionsList.forEach { transaction ->
                if (transaction.category == categoryItem.category && transaction.type == categoryItem.type) {
                    sum += transaction.value ?: 0.0
                }
            }
            resultList.add(
                UIModel.TransactionModel(
                    type = categoryItem.type,
                    category = categoryItem.category,
                    value = sum
                )
            )
        }
        resultList = resultList.filter { (it.value ?: 0.0) > 0.0 }.sortedByDescending { it.value }.toMutableList()
        return resultList
    }
}