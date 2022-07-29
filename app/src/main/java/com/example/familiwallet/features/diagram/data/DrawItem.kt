package com.example.familiwallet.features.diagram.data

import androidx.compose.ui.graphics.Color

class DrawItem (
    var radius: Float = 300f,
    val startAngle: Float,
    val sweepAngle: Float,
    val value: Double,
    val color: Color
)