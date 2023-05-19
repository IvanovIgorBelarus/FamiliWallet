package com.example.common

enum class CategoryType (val position: Int, val type: String){
    INCOME(1, com.example.common.INCOMES),
    EXPENSE(0, com.example.common.EXPENSES),
    BANKS(2, com.example.common.BANK),
    UNKNOWN(-1, "");

    companion object{
        fun getCategory(position: Int) = values().firstOrNull { it.position == position }?: UNKNOWN
    }
}