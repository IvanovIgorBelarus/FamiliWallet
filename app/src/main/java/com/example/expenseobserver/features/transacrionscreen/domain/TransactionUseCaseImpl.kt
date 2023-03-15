package com.example.expenseobserver.features.transacrionscreen.domain

import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.repository.DataRepository
import javax.inject.Inject

class TransactionUseCaseImpl @Inject constructor(
    private val repo: DataRepository
) : TransactionUseCase {
    override suspend fun doTransaction(request: UIModel.TransactionModel): DataResponse<Unit> =
        repo.addItem(request)

    override suspend fun getTransactionsList(forceLoad: Boolean): DataResponse<List<UIModel.TransactionModel>>? =
        repo.getTransactionsList(forceLoad)

    override suspend fun deleteTransaction(request: UIModel.TransactionModel): DataResponse<Unit> =
        repo.deleteItem(request)
}