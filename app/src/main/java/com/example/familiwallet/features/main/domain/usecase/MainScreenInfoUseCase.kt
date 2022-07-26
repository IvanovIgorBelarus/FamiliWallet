package com.example.familiwallet.features.main.domain.usecase

import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel

interface MainScreenInfoUseCase {

    suspend fun getCategoriesList(): DataResponse<List<UIModel.CategoryModel>>

    suspend fun getTransactionsList(): DataResponse<List<UIModel.TransactionModel>>?
}