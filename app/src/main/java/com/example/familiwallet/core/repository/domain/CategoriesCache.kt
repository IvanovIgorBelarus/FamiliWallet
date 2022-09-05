package com.example.familiwallet.core.repository.domain

import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel

object CategoriesCache : CacheRepository<DataResponse<List<UIModel.CategoryModel>>> {

    private val categoriesList = mutableSetOf<UIModel.CategoryModel>()

    override suspend fun put(cache: DataResponse<List<UIModel.CategoryModel>>) {
        val list = (cache as DataResponse.Success).data
        categoriesList.addAll(list)
    }

    override suspend fun get(): DataResponse<List<UIModel.CategoryModel>> = DataResponse.Success(categoriesList.toList())

    override suspend fun clear() {
        categoriesList.clear()
    }

    override suspend fun isEmpty(): Boolean = categoriesList.isEmpty()
}