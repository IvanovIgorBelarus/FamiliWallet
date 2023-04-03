package com.example.expenseobserver.core.common

enum class CategoryType (val position: Int, val type: String){
    INCOME(1, INCOMES),
    EXPENSE(0, EXPENSES),
    BANKS(2, BANK),
    UNKNOWN(-1, "");

    companion object{
        fun getCategory(position: Int) = values().firstOrNull { it.position == position }?: UNKNOWN
    }
}