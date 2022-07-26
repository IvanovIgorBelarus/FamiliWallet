package com.example.familiwallet.core.repository.domain

import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel

object TransactionsCache: CacheRepository<DataResponse<List<UIModel.TransactionModel>>> {

    private var transactionsList: DataResponse<List<UIModel.TransactionModel>>? = null

    override fun put(cache: DataResponse<List<UIModel.TransactionModel>>) {
       transactionsList = cache
    }

    override fun get(): DataResponse<List<UIModel.TransactionModel>>? = transactionsList

    override fun clear() {
        transactionsList = null
    }

    override fun isEmpty(): Boolean = transactionsList == null
}