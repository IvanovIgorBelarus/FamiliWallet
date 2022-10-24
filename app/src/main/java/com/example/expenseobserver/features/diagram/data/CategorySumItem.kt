package com.example.expenseobserver.features.diagram.data

data class CategorySumItem(
    var category: String? = null,
    var sum: Double = 0.0,
    val icon: Int,
    val color: String
)
