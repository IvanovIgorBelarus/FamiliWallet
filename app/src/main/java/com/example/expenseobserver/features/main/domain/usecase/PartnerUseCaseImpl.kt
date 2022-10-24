package com.example.expenseobserver.features.main.domain.usecase

import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.repository.DataRepository
import javax.inject.Inject

class PartnerUseCaseImpl @Inject constructor(
    private val repo: DataRepository
) : PartnerUseCase {
    override suspend fun addPartner(accountModel: UIModel.AccountModel): DataResponse<Unit> =
        repo.addPartner(accountModel)

    override suspend fun getPartner(forceLoad: Boolean): DataResponse<UIModel.AccountModel>? =
        repo.getPartner(forceLoad)
}