package com.example.expenseobserver.features.diagram.data

import androidx.compose.ui.graphics.Color

class DrawItem (
    var radius: Float = 340f,
    val startAngle: Float,
    val sweepAngle: Float,
    val value: Double,
    val color: Color,
    val icon: Int
)