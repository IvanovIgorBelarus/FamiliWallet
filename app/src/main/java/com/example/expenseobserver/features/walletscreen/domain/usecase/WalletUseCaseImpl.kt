package com.example.expenseobserver.features.walletscreen.domain.usecase

import com.example.expenseobserver.core.BaseUseCaseImpl
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import javax.inject.Inject

class WalletUseCaseImpl @Inject constructor(): BaseUseCaseImpl(), WalletUseCase {
    override suspend fun getWalletsList(forceLoad: Boolean): DataResponse<List<UIModel.WalletModel>>? =
        repo.getWalletsList(forceLoad)
}