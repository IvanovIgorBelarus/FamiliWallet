package com.example.familiwallet.core.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import com.example.familiwallet.App.Companion.dateFilterType
import com.example.familiwallet.App.Companion.endDate
import com.example.familiwallet.App.Companion.startDate
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.utils.toEndOfDay
import com.example.familiwallet.core.utils.toStartOfDay
import com.example.familiwallet.ui.theme.backgroundColor
import java.util.*
import kotlin.math.roundToInt

fun List<UIModel.TransactionModel>.typeFilter(type: String): List<UIModel.TransactionModel> =
    this.filter { item -> item.type == type }

fun List<UIModel.TransactionModel>.balanceFilter(): Double = this.sumByDouble {
    when (it.type) {
        EXPENSES -> -it.value!!
        INCOMES -> it.value!!
        else -> 0.0
    }
}

fun List<UIModel.TransactionModel>.transactionsPartnersFilter(partner: UIModel.AccountModel): List<UIModel.TransactionModel> =
    this.filter { (it.uid == partner.uid) || (it.uid == partner.partnerUid) }

fun List<UIModel.TransactionModel>.currentDayFilter(): List<UIModel.TransactionModel> {
    val firstDay = Calendar.getInstance().time.toStartOfDay.time
    val lastDay = Calendar.getInstance().time.toEndOfDay.time
    return this.filter { it.date!! in firstDay..lastDay }
}

fun List<UIModel.TransactionModel>.currentWeekFilter(): List<UIModel.TransactionModel> {
    val firstDay = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -6) }.time.toStartOfDay.time
    val lastDay = Calendar.getInstance().time.toEndOfDay.time
    return this.filter { it.date!! in firstDay..lastDay }
}

fun List<UIModel.TransactionModel>.currentMonthFilter(): List<UIModel.TransactionModel> {
    val firstDayOfMonth = Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH)) }.time.toStartOfDay.time
    val lastDayOfMonth = Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)) }.time.toEndOfDay.time
    return this.filter { it.date!! in firstDayOfMonth..lastDayOfMonth }
}

fun List<UIModel.TransactionModel>.currentRangeFilter(): List<UIModel.TransactionModel> {
    return this.filter { it.date!! in startDate!!..endDate!! }
}


fun List<UIModel.TransactionModel>.currentDateFilter(): List<UIModel.TransactionModel> {
    return when (dateFilterType) {
        TimeRangeType.DAY -> this.currentMonthFilter()
        TimeRangeType.WEEK -> this.currentWeekFilter()
        TimeRangeType.MONTH -> this.currentDayFilter()
//        RANGE_FILTER -> this.currentRangeFilter()
        else -> this
    }
}

fun List<UIModel.CategoryModel>.categoryPartnersFilter(partner: UIModel.AccountModel?): List<UIModel.CategoryModel> =
    this.filter { (it.uid == partner?.uid) || (it.uid == partner?.partnerUid) }

fun List<UIModel.CategoryModel>.categoryTypeFilter(type: String): List<UIModel.CategoryModel> = this.filter { it.type == type }

fun List<UIModel.TransactionModel>.userFilter(uid: String): List<UIModel.TransactionModel> = this.filter { it.uid == uid }.sortedByDescending { it.date }

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