package com.example.expenseobserver.features.transacrionscreen.domain

import com.example.expenseobserver.core.BaseUseCase
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel

interface TransactionUseCase: BaseUseCase {
    suspend fun getTransactionsList(forceLoad: Boolean = false): DataResponse<List<UIModel.TransactionModel>>?
}