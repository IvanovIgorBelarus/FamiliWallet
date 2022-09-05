package com.example.familiwallet.core.repository.domain

import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel

object TransactionsCache: CacheRepository<DataResponse<List<UIModel.TransactionModel>>> {

    private val transactionsList = mutableSetOf<UIModel.TransactionModel>()

    override suspend fun put(cache: DataResponse<List<UIModel.TransactionModel>>) {
        val list = (cache as DataResponse.Success).data
       transactionsList.addAll(list)
    }

    override suspend fun get(): DataResponse<List<UIModel.TransactionModel>> = DataResponse.Success(transactionsList.toList())

    override suspend fun clear() {
        transactionsList.clear()
    }

    override suspend fun isEmpty(): Boolean = transactionsList.isEmpty()
}