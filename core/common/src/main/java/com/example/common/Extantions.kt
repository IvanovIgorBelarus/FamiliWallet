package com.example.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt

fun List<com.example.data.UIModel.TransactionModel>.typeFilter(type: String): List<com.example.data.UIModel.TransactionModel> =
    this.filter { item -> item.type == type }

fun List<com.example.data.UIModel.TransactionModel>.balanceFilter(): Double = this.sumOf {
    when (it.type) {
        EXPENSES -> -it.value!!
        INCOMES -> it.value!!
        else -> 0.0
    }
}

fun List<com.example.data.UIModel.TransactionModel>.currentDateFilter(): List<com.example.data.UIModel.TransactionModel> =
    this.filter { (it.date ?: 0L) in TimeRangeType.MONTH.startDate..TimeRangeType.MONTH.endDate }

fun List<com.example.data.UIModel.CategoryModel>.categoryTypeFilter(type: String): List<com.example.data.UIModel.CategoryModel> = this.filter { it.type == type }

val Double.round: Double
    get() = (this * 100).roundToInt().toDouble() / 100

fun Double?.formatAmount(): String = String.format("%.2f", this ?: 0.0)

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

inline fun Modifier.rippleClickable(color: Color, crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(bounded = true, color = color)
    ) {
        onClick()
    }
}

@OptIn(ExperimentalFoundationApi::class)
inline fun Modifier.longRippleClickable(
    color: Color,
    crossinline onClick: () -> Unit = {},
    crossinline onLongClick: () -> Unit = {}
): Modifier = composed {
    combinedClickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(bounded = true, color = color),
        onLongClick = { onLongClick() },
        onClick = { onClick() }
    )
}