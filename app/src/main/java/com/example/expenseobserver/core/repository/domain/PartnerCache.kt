package com.example.expenseobserver.core.repository.domain

import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel

object PartnerCache : CacheRepository<DataResponse<UIModel.AccountModel>> {

    private var partner: DataResponse<UIModel.AccountModel>? = null

    override suspend fun put(collectionName: String, cache: DataResponse<UIModel.AccountModel>) {
        partner = cache
    }

    override suspend fun get(collectionName: String): DataResponse<UIModel.AccountModel>? = partner


    override suspend fun clear(collectionName: String) {
        partner = null
    }

    override suspend fun isEmpty(collectionName: String): Boolean = partner == null
}