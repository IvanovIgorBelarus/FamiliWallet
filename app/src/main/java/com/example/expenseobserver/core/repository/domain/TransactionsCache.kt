package com.example.expenseobserver.core.repository.domain

import android.util.Log
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel

object TransactionsCache: CacheRepository<DataResponse<List<UIModel.TransactionModel>>> {

    private val transactionsList = mutableSetOf<UIModel.TransactionModel>()

    override suspend fun put(cache: DataResponse<List<UIModel.TransactionModel>>) {
        val list = (cache as DataResponse.Success).data
       transactionsList.addAll(list)
    }

    override suspend fun get(): DataResponse<List<UIModel.TransactionModel>> {
        Log.d("MYNAME", "TransactionsCache")
        return DataResponse.Success(transactionsList.toList())
    }

    override suspend fun clear() {
        transactionsList.clear()
    }

    override suspend fun isEmpty(): Boolean = transactionsList.isEmpty()
}