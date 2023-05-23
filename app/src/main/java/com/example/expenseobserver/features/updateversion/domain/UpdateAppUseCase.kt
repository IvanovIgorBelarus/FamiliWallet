package com.example.expenseobserver.features.updateversion.domain

import com.example.mylibrary.base.BaseUseCase
import com.example.data.DataResponse
import com.example.data.UIModel

interface UpdateAppUseCase: com.example.mylibrary.base.BaseUseCase {
    suspend fun checkUpdates(): DataResponse<UIModel.UpdateModel>
}