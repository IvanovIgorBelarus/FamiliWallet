package com.example.expenseobserver.features.category.domain.usecase

import com.example.expenseobserver.core.BaseUseCase
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel

interface CategoriesUseCase: BaseUseCase {
    suspend fun getCategoriesList(forceLoad: Boolean = false): DataResponse<List<UIModel.CategoryModel>>?
}