package com.example.familiwallet.core.repository.domain

import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel

object CategoriesCache : CacheRepository<DataResponse<List<UIModel.CategoryModel>>> {

    private var categoriesList: DataResponse<List<UIModel.CategoryModel>>? = null

    override fun put(cache: DataResponse<List<UIModel.CategoryModel>>) {
        categoriesList = cache
    }

    override fun get(): DataResponse<List<UIModel.CategoryModel>>? = categoriesList

    override fun clear() {
        categoriesList = null
    }

    override fun isEmpty(): Boolean = categoriesList == null
}