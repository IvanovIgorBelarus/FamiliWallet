package com.example.familiwallet.features.start_screen.domain.usecase

import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel

interface StartScreenInfoUseCase {

    suspend fun getCategoriesList(uid: String, forceLoad: Boolean = false): DataResponse<List<UIModel.CategoryModel>>

    suspend fun getTransactionsList(uid: String, forceLoad: Boolean = false): DataResponse<List<UIModel.TransactionModel>>?
}