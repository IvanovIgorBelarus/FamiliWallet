package com.example.familiwallet.core.common

enum class CategoryType (val position: Int, val type: String){
    INCOME(0, INCOMES),
    EXPENSE(1, EXPENSES),
    BANKS(2, BANK),
    UNKNOWN(-1, "");

    companion object{
        fun getCategory(position: Int) = values().firstOrNull { it.position == position }?: UNKNOWN
    }
}