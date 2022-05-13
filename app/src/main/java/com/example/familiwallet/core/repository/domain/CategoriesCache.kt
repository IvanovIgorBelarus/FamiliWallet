package com.example.familiwallet.core.repository.domain

import com.example.familiwallet.core.data.UIModel

object CategoriesCache : CacheRepository<List<UIModel.CategoryModel>> {

    private var categoriesList: List<UIModel.CategoryModel>? = null

    override fun put(cache: List<UIModel.CategoryModel>) {
        categoriesList = cache
    }

    override fun get(): List<UIModel.CategoryModel> = categoriesList.orEmpty()

    override fun clear() {
        categoriesList = null
    }

    override fun isEmpty(): Boolean = categoriesList.isNullOrEmpty()
}