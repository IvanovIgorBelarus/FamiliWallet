package com.example.expenseobserver.features.transacrionscreen

import com.example.expenseobserver.core.data.UIModel

object TransactionMapper {
    fun mapCategoryQueue(categoryList: List<UIModel.CategoryModel>, transactionList: List<UIModel.TransactionModel>): List<UIModel.CategoryModel> {
        categoryList.forEach { category ->
            var count = 0
            transactionList.forEach { transaction ->
                if (transaction.category == category.category) {
                    count++
                }
            }
            category.count = count
        }
        return categoryList.sortedByDescending { it.count }
    }
}