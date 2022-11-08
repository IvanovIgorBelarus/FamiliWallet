package com.example.expenseobserver.features.start_screen.domain.usecase

import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel

interface CategoriesUseCase {
    suspend fun getCategoriesList(forceLoad: Boolean = false): DataResponse<List<UIModel.CategoryModel>>?

    suspend fun updateCategory(category: UIModel.CategoryModel): DataResponse<Unit>

    suspend fun addNewCategory(category: UIModel.CategoryModel): DataResponse<Unit>

    suspend fun deleteCategory(category: UIModel.CategoryModel): DataResponse<Unit>
}