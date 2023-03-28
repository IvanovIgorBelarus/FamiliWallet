package com.example.expenseobserver.features.category.domain.usecase

import com.example.expenseobserver.core.BaseUseCaseImpl
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import javax.inject.Inject

class CategoriesUseCaseImpl @Inject constructor() : BaseUseCaseImpl(), CategoriesUseCase {
    override suspend fun getCategoriesList(forceLoad: Boolean): DataResponse<List<UIModel.CategoryModel>>? =
        repo.getCategoriesList(forceLoad)
}