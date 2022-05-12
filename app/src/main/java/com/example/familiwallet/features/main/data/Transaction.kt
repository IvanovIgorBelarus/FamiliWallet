package com.example.familiwallet.features.main.data

import com.example.familiwallet.core.common.CategoryType

data class Transaction(
    val type: CategoryType,
    val value: String,
    val category: String
)
