package com.example.expenseobserver.core.common

enum class CashType(val type: String) {
    CASHES(CASH),
    CARDS(CARD),
    UNKNOWN("");

    companion object {
        fun getCashType(type: String) = values().firstOrNull { it.type == type } ?: UNKNOWN
    }
}