package com.example.familiwallet.features.diagram

import com.example.familiwallet.core.data.AppIcons
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.features.diagram.data.CategorySumItem
import com.example.familiwallet.features.diagram.data.DrawItem
import com.example.familiwallet.ui.theme.diagramColor0
import com.example.familiwallet.ui.theme.diagramColor1
import com.example.familiwallet.ui.theme.diagramColor2
import com.example.familiwallet.ui.theme.diagramColor3
import com.example.familiwallet.ui.theme.diagramColor4
import com.example.familiwallet.ui.theme.diagramColor5
import com.example.familiwallet.ui.theme.diagramColor6

object DiagramMapper {
    fun mapDiagramItems(
        expansesList: List<UIModel.TransactionModel>,
        categoriesList: List<UIModel.CategoryModel>
    ): List<CategorySumItem> {
        val sumList = mutableListOf<CategorySumItem>()
        categoriesList.forEach { categoryItem -> sumList.add(CategorySumItem(category = categoryItem.category, icon = AppIcons.getImageRes(categoryItem.icon).imageRes)) }
        expansesList.forEach { transaction ->
            sumList.forEach { categorySum ->
                if (transaction.category == categorySum.category) {
                    categorySum.sum += transaction.value ?: 0.0
                }
            }
        }
        var resultList = sumList.sortedByDescending { it.sum }.subList(0, 6)
        val overList = sumList.sortedByDescending { it.sum }.subList(7, sumList.size - 1)
        val lastItem = CategorySumItem(category = "other", icon = AppIcons.UNKNOWN.imageRes).apply {
            overList.forEach { item -> sum += item.sum }
        }
        resultList = resultList.plus(lastItem)
        return resultList
    }

    fun getSum(expansesList: List<CategorySumItem>): Double {
        var result = 0.0
        expansesList.forEach { result += it.sum }
        return result
    }

    fun mapDrawItems(expansesList: List<CategorySumItem>, summary: Double): List<DrawItem> {
        val items = mutableListOf<DrawItem>()
        var startAngle = -90f
        var position = 0

        expansesList.forEach { value ->
            val onePart = 360f / summary.toFloat()
            val sweepAngle = value.sum.toFloat() * onePart
            items.add(
                DrawItem(
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    value = value.sum,
                    color = colorList[position],
                    icon = value.icon
                )
            )
            startAngle += sweepAngle
            position++
        }
        return items
    }

    private val colorList = listOf(
        diagramColor0,
        diagramColor1,
        diagramColor2,
        diagramColor3,
        diagramColor4,
        diagramColor5,
        diagramColor6
    )
}