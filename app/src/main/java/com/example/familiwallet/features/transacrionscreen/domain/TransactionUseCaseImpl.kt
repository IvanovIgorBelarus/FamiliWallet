package com.example.familiwallet.features.transacrionscreen.domain

import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.repository.DataRepository
import javax.inject.Inject

class TransactionUseCaseImpl @Inject constructor(
    private val repo: DataRepository
) : TransactionUseCase {
    override suspend fun doTransaction(request: UIModel.TransactionModel): DataResponse<Unit> =
        repo.doTransaction(request)

    override suspend fun getTransactionsList(forceLoad: Boolean): DataResponse<List<UIModel.TransactionModel>>? =
        repo.getTransactionsList(forceLoad)
}