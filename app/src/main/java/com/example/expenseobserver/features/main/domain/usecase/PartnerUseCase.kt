package com.example.expenseobserver.features.main.domain.usecase

import com.example.expenseobserver.core.data.DataResponse
import com.example.data.UIModel

interface PartnerUseCase {
    suspend fun addPartner(accountModel: UIModel.AccountModel): DataResponse<Unit>
    suspend fun getPartner(forceLoad: Boolean = false): DataResponse<UIModel.AccountModel>?
}