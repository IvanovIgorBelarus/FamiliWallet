package com.example.familiwallet.features.transacrionscreen.domain

import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel

interface TransactionUseCase {
    suspend fun doTransaction(request: UIModel.TransactionModel ): DataResponse<Unit>
}