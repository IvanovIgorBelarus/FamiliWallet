package com.example.familiwallet.features.start_screen.domain.usecase

import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel

interface CategoriesUseCase {
    suspend fun getCategoriesList(forceLoad: Boolean = false): DataResponse<List<UIModel.CategoryModel>>?

    suspend fun updateCategory(category: UIModel.CategoryModel): DataResponse<Unit>

    suspend fun addNewCategory(category: UIModel.CategoryModel): DataResponse<Unit>
}