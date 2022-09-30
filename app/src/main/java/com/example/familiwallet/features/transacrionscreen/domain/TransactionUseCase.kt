package com.example.familiwallet.features.transacrionscreen.domain

import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel

interface TransactionUseCase {
    suspend fun doTransaction(request: UIModel.TransactionModel): DataResponse<Unit>

    suspend fun getTransactionsList(forceLoad: Boolean = false): DataResponse<List<UIModel.TransactionModel>>?

    suspend fun deleteTransaction(request: UIModel.TransactionModel): DataResponse<Unit>
}