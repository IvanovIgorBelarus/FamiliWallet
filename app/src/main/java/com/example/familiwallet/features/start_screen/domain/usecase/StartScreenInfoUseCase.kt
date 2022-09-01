package com.example.familiwallet.features.start_screen.domain.usecase

import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel

interface StartScreenInfoUseCase {

    suspend fun getCategoriesList(uid: String): DataResponse<List<UIModel.CategoryModel>>

    suspend fun getTransactionsList(uid: String): DataResponse<List<UIModel.TransactionModel>>?
}