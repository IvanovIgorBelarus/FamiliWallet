package com.alseda.updateapp.domain

import com.example.mylibrary.base.BaseUseCaseImpl
import com.example.data.DataResponse
import com.example.data.UIModel
import javax.inject.Inject

class UpdateAppUseCaseImpl @Inject constructor() : BaseUseCaseImpl(), com.alseda.updateapp.domain.UpdateAppUseCase {
    override suspend fun checkUpdates(): DataResponse<UIModel.UpdateModel> =
        repo.checkUpdates()
}