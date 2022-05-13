package com.example.familiwallet.core.repository.domain

import com.example.familiwallet.core.data.UIModel

object TransactionsCache: CacheRepository<List<UIModel.TransactionModel>> {

    private var transactionsList: List<UIModel.TransactionModel>? = null

    override fun put(cache: List<UIModel.TransactionModel>) {
       transactionsList = cache
    }

    override fun get(): List<UIModel.TransactionModel> = transactionsList.orEmpty()

    override fun clear() {
        transactionsList = null
    }

    override fun isEmpty(): Boolean = transactionsList.isNullOrEmpty()
}