package com.example.familiwallet.features.diagram

import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.features.diagram.data.CategorySumItem

object DiagramMapper {
    fun mapDiagramItems(
        expansesList: List<UIModel.TransactionModel>,
        categoriesList: List<UIModel.CategoryModel>
    ): List<CategorySumItem> {
        val resultList = mutableListOf<CategorySumItem>()
        categoriesList.forEach { categoryItem -> resultList.add(CategorySumItem(category = categoryItem.category)) }
        expansesList.forEach { transaction ->
            resultList.forEach { categorySum ->
                if (transaction.category == categorySum.category) {
                    categorySum.sum += transaction.value ?: 0.0
                }
            }
        }
        return resultList
    }
}