package com.example.expenseobserver.features.updateversion.domain

import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel

interface UpdateAppUseCase  {
    suspend fun checkUpdates(): DataResponse<UIModel.UpdateModel>
}