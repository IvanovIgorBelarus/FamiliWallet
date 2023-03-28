package com.example.expenseobserver.features.transacrionscreen.domain

import com.example.expenseobserver.core.BaseUseCaseImpl
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.repository.DataRepository
import javax.inject.Inject

class TransactionUseCaseImpl @Inject constructor() : BaseUseCaseImpl(), TransactionUseCase {
    override suspend fun getTransactionsList(forceLoad: Boolean): DataResponse<List<UIModel.TransactionModel>>? =
        repo.getTransactionsList(forceLoad)
}