package com.example.expenseobserver.features.updateversion.domain

import com.example.expenseobserver.core.BaseUseCaseImpl
import com.example.expenseobserver.core.data.DataResponse
import com.example.data.UIModel
import javax.inject.Inject

class UpdateAppUseCaseImpl @Inject constructor() : BaseUseCaseImpl(), UpdateAppUseCase {
    override suspend fun checkUpdates(): DataResponse<UIModel.UpdateModel> =
        repo.checkUpdates()
}