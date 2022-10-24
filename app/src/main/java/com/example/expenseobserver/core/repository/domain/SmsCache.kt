package com.example.expenseobserver.core.repository.domain

import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel

object SmsCache : CacheRepository<DataResponse<List<UIModel.SmsModel>>> {

    private var smsList: DataResponse<List<UIModel.SmsModel>>? = null

    override suspend fun put(cache: DataResponse<List<UIModel.SmsModel>>) {
            smsList = cache
    }

    override suspend fun get(): DataResponse<List<UIModel.SmsModel>>? = smsList

    override suspend fun clear() {
        smsList = null
    }

    override suspend fun isEmpty(): Boolean = smsList == null
}