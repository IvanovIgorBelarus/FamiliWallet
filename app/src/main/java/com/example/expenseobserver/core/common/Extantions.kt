package com.example.expenseobserver.core.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import com.example.expenseobserver.App.Companion.dateFilterType
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.utils.toStartOfDay
import com.example.expenseobserver.ui.theme.backgroundColor
import java.util.*
import java.util.Calendar.DATE
import kotlin.math.roundToInt

fun List<UIModel.TransactionModel>.typeFilter(type: String): List<UIModel.TransactionModel> =
    this.filter { item -> item.type == type }

fun List<UIModel.TransactionModel>.balanceFilter(): Double = this.sumOf {
    when (it.type) {
        EXPENSES -> -it.value!!
        INCOMES -> it.value!!
        else -> 0.0
    }
}

fun List<UIModel.TransactionModel>.currentDateFilter(): List<UIModel.TransactionModel> =
    this.filter { (it.date ?: 0L) in dateFilterType.startDate..dateFilterType.endDate }

fun List<UIModel.TransactionModel>.mapDataToStartOfDay(): List<UIModel.TransactionModel> = this.map {
    it.date = Date(it.date ?: 0).toStartOfDay.time
    it
}

fun List<UIModel.CategoryModel>.categoryTypeFilter(type: String): List<UIModel.CategoryModel> = this.filter { it.type == type }

fun List<UIModel.TransactionModel>.categoryFilter(category: String): List<UIModel.TransactionModel> = this.filter { it.category == category }.sortedByDescending { it.date }

val Double.round: Double
    get() = (this * 100).roundToInt().toDouble() / 100

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

inline fun Modifier.rippleClickable(color: Color = backgroundColor, crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(bounded = true, color = color)
    ) {
        onClick()
    }
}

@OptIn(ExperimentalFoundationApi::class)
inline fun Modifier.longRippleClickable(color: Color = backgroundColor, crossinline onClick: () -> Unit): Modifier = composed {
    combinedClickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(bounded = true, color = color),
        onLongClick = { onClick() },
        onClick = {}
    )
}