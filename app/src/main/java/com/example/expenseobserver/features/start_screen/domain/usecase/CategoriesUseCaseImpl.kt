package com.example.expenseobserver.features.start_screen.domain.usecase

import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.repository.DataRepository
import javax.inject.Inject

class CategoriesUseCaseImpl @Inject constructor(
    private val repo: DataRepository
) : CategoriesUseCase {
    override suspend fun getCategoriesList(forceLoad: Boolean): DataResponse<List<UIModel.CategoryModel>>? =
        repo.getCategoriesList(forceLoad)

    override suspend fun updateCategory(category: UIModel.CategoryModel): DataResponse<Unit> =
        repo.upDateItem(category)

    override suspend fun addNewCategory(category: UIModel.CategoryModel): DataResponse<Unit> =
        repo.addNewCategory(category)
}