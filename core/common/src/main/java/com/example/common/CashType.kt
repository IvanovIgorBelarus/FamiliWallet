package com.example.common

enum class CashType(val type: String) {
    CASHES(com.example.common.CASH),
    CARDS(com.example.common.CARD),
    UNKNOWN("");

    companion object {
        fun getCashType(type: String) = values().firstOrNull { it.type == type } ?: UNKNOWN
    }
}