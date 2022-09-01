package com.example.familiwallet.features.main.domain.usecase

import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel

interface PartnerUseCase {
    suspend fun addPartner(accountModel: UIModel.AccountModel): DataResponse<Unit>
    suspend fun getPartner(forceLoad: Boolean = false): DataResponse<UIModel.AccountModel>?
}