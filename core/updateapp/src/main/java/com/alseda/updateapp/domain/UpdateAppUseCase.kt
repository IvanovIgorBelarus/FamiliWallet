package com.alseda.updateapp.domain

import com.example.mylibrary.base.BaseUseCase
import com.example.data.DataResponse
import com.example.data.UIModel

interface UpdateAppUseCase: BaseUseCase {
    suspend fun checkUpdates(): DataResponse<UIModel.UpdateModel>
}