//package com.example.expenseobserver.core.repository.domain
//
//import com.example.expenseobserver.core.data.DataResponse
//import com.example.expenseobserver.core.data.UIModel
//
//object CategoriesCache : CacheRepository<DataResponse<List<UIModel.CategoryModel>>> {
//
//    private val categoriesList = mutableSetOf<UIModel.CategoryModel>()
//
//    override suspend fun put(cache: DataResponse<List<UIModel.CategoryModel>>) {
//        val list = (cache as DataResponse.Success).data
//        categoriesList.addAll(list)
//    }
//
//    override suspend fun get(): DataResponse<List<UIModel.CategoryModel>> {
//        return DataResponse.Success(categoriesList.toList())
//    }
//
//    override suspend fun clear() {
//        categoriesList.clear()
//    }
//
//    override suspend fun isEmpty(): Boolean = categoriesList.isEmpty()
//}