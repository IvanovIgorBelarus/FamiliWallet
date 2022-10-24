package com.example.expenseobserver.features.transacrionscreen.domain

import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel

interface TransactionUseCase {
    suspend fun doTransaction(request: UIModel.TransactionModel): DataResponse<Unit>

    suspend fun getTransactionsList(forceLoad: Boolean = false): DataResponse<List<UIModel.TransactionModel>>?

    suspend fun deleteTransaction(request: UIModel.TransactionModel): DataResponse<Unit>
}