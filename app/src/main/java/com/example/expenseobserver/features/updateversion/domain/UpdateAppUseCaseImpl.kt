package com.example.expenseobserver.features.updateversion.domain

import com.example.mylibrary.base.BaseUseCaseImpl
import com.example.data.DataResponse
import com.example.data.UIModel
import javax.inject.Inject

class UpdateAppUseCaseImpl @Inject constructor() : BaseUseCaseImpl(), UpdateAppUseCase {
    override suspend fun checkUpdates(): DataResponse<UIModel.UpdateModel> =
        repo.checkUpdates()
}