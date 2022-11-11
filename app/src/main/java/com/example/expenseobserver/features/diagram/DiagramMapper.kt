package com.example.expenseobserver.features.diagram

import com.example.expenseobserver.core.common.CategoryType
import com.example.expenseobserver.core.common.categoryTypeFilter
import com.example.expenseobserver.core.common.typeFilter
import com.example.expenseobserver.core.data.AppIcons
import com.example.expenseobserver.core.data.CategoryColor
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.features.diagram.data.CategorySumItem
import com.example.expenseobserver.features.diagram.data.DrawItem
import com.example.expenseobserver.ui.theme.diagramColor0
import com.example.expenseobserver.ui.theme.diagramColor1
import com.example.expenseobserver.ui.theme.diagramColor2
import com.example.expenseobserver.ui.theme.diagramColor3
import com.example.expenseobserver.ui.theme.diagramColor4
import com.example.expenseobserver.ui.theme.diagramColor5
import com.example.expenseobserver.ui.theme.diagramColor6

object DiagramMapper {
    fun mapDiagramItems(
        expansesList: List<UIModel.TransactionModel>,
        categoriesList: List<UIModel.CategoryModel>
    ): List<CategorySumItem> {
        val sumList = mutableListOf<CategorySumItem>()
        categoriesList.categoryTypeFilter(CategoryType.EXPENSE.type).forEach { categoryItem ->
            sumList.add(
                CategorySumItem(
                    category = categoryItem.category,
                    icon = AppIcons.getImageRes(categoryItem.icon).imageRes,
                    color = categoryItem.color.orEmpty()
                )
            )
        }
        expansesList.typeFilter(CategoryType.EXPENSE.type).forEach { transaction ->
            sumList.forEach { categorySum ->
                if (transaction.category == categorySum.category) {
                    categorySum.sum += transaction.value ?: 0.0
                }
            }
        }

        var resultList = if (sumList.size > 7) {
            sumList.sortedByDescending { it.sum }.subList(0, 6)
        } else {
            sumList.sortedByDescending { it.sum }
        }
        if (sumList.size > 7) {
            val overList = sumList.sortedByDescending { it.sum }.subList(7, sumList.size - 1)
            val lastItem = CategorySumItem(
                category = "other",
                icon = AppIcons.UNKNOWN.imageRes,
                color = CategoryColor.UNKNOWN.name
            ).apply {
                overList.forEach { item -> sum += item.sum }
            }
            resultList = resultList.plus(lastItem)
        }
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
                    color = CategoryColor.getColor(value.color).color,
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