package com.example.expenseobserver.core.common

import com.example.expenseobserver.core.utils.toEndOfDay
import com.example.expenseobserver.core.utils.toStartOfDay
import java.util.*

enum class TimeRangeType(val text: String, var startDate: Long, var endDate: Long, val position: Int) {
    DAY(
        "День",
        startDate = Calendar.getInstance().time.toStartOfDay.time,
        endDate = Calendar.getInstance().time.toEndOfDay.time,
        0
    ),
    WEEK(
        "Неделя",
        startDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -6) }.time.toStartOfDay.time,
        endDate = Calendar.getInstance().time.toEndOfDay.time,
        1
    ),
    MONTH(
        "Месяц",
        startDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -30) }.time.toStartOfDay.time,
        endDate = Calendar.getInstance().time.toEndOfDay.time,
        2
    ),
    RANGE(
        "Период",
        startDate = Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH) - 31) }.time.toStartOfDay.time,
        endDate = Calendar.getInstance().time.toEndOfDay.time,
        3
    ),
    UNKNOWN(
        "",
        startDate = -1,
        endDate = -1,
        -1
    );
//    MONTH(
//        "Месяц",
//        startDate = Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH)) }.time.toStartOfDay.time,
//        endDate = Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)) }.time.toEndOfDay.time
//    );

    companion object {
        fun getTimeRangeType(name: String) = values().firstOrNull { it.name == name } ?: MONTH
        fun getTimeRangeType(position: Int) = values().firstOrNull { it.position == position } ?: MONTH
    }
}