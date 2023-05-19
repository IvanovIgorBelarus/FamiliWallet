package com.example.expenseobserver.features.updateversion.domain

import com.example.expenseobserver.core.BaseUseCase
import com.example.expenseobserver.core.data.DataResponse
import com.example.data.UIModel

interface UpdateAppUseCase: BaseUseCase  {
    suspend fun checkUpdates(): DataResponse<UIModel.UpdateModel>
}