package com.example.expenseobserver.features.main.domain.usecase

import com.example.data.DataResponse
import com.example.data.UIModel
import com.example.mylibrary.repo.DataRepository
import javax.inject.Inject

class PartnerUseCaseImpl @Inject constructor(
    private val repo: DataRepository
) : PartnerUseCase {
    override suspend fun addPartner(accountModel: UIModel.AccountModel): DataResponse<Unit> =
        repo.addItem(accountModel)

    override suspend fun getPartner(forceLoad: Boolean): DataResponse<UIModel.AccountModel>? =
        repo.getPartner(forceLoad)
}