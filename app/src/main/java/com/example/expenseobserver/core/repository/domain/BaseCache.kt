package com.example.expenseobserver.core.repository.domain

import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel

object BaseCache : CacheRepository<DataResponse<List<UIModel>>> {

    private val cacheMap = mutableMapOf<String, DataResponse<List<UIModel>>>()

    override suspend fun put(collectionName: String, data: DataResponse<List<UIModel>>) {
        if (isEmpty(collectionName)) {
            cacheMap[collectionName] = data
        }
    }

    override suspend fun get(collectionName: String): DataResponse<List<UIModel>>? =
        cacheMap[collectionName]


    override suspend fun clear(collectionName: String) {
        cacheMap.remove(collectionName)
    }

    override suspend fun isEmpty(collectionName: String): Boolean = (cacheMap[collectionName] as? DataResponse.Success)?.data.isNullOrEmpty()
}