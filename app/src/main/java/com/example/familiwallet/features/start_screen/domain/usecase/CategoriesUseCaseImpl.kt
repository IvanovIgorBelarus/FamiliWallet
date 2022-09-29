package com.example.familiwallet.features.start_screen.domain.usecase

import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.repository.DataRepository
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