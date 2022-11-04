package com.example.expenseobserver.features.updateversion.domain

import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.repository.DataRepository
import javax.inject.Inject

class UpdateAppUseCaseImpl @Inject constructor(
    private val repo: DataRepository
) : UpdateAppUseCase {
    override suspend fun checkUpdates(): DataResponse<UIModel.UpdateModel> =
        repo.checkUpdates()
}