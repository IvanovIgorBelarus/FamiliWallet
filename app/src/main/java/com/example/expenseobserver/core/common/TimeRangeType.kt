package com.example.expenseobserver.core.common

import com.example.expenseobserver.core.utils.toEndOfDay
import com.example.expenseobserver.core.utils.toStartOfDay
import java.util.*

enum class TimeRangeType(val text: String, val startDate: Long, val endDate: Long) {
    DAY(
        "День",
        startDate = Calendar.getInstance().time.toStartOfDay.time,
        endDate = Calendar.getInstance().time.toEndOfDay.time
    ),
    WEEK(
        "Неделя",
        startDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -6) }.time.toStartOfDay.time,
        endDate = Calendar.getInstance().time.toEndOfDay.time
    ),
    MONTH(
        "Месяц",
        startDate = Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH)) }.time.toStartOfDay.time,
        endDate = Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)) }.time.toEndOfDay.time
    );

    companion object {
        fun getTimeRangeType(name: String) = values().firstOrNull { it.name == name } ?: MONTH
    }
}