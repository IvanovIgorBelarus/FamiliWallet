package com.example.familiwallet.features.main.domain.usecase

import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.repository.DataRepository
import javax.inject.Inject

class MainScreenInfoUseCaseImpl @Inject constructor(
    private val repo: DataRepository
) : MainScreenInfoUseCase {
    override suspend fun getCategoriesList(): DataResponse<List<UIModel.CategoryModel>> {
        return when (val response = repo.getCategoriesList()) {
            is DataResponse.Success -> DataResponse.Success(
                response.data
            )
            is DataResponse.Error -> DataResponse.Error(response.exception)
            else -> DataResponse.Error(Throwable("Нет данных"))
        }
    }

    override suspend fun getTransactionsList(): DataResponse<List<UIModel.TransactionModel>>? =
        repo.getTransactionsList()
}