package com.example.expenseobserver.features.walletscreen.domain.usecase

import com.example.expenseobserver.core.BaseUseCase
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel

interface WalletUseCase: BaseUseCase {
    suspend fun getWalletsList(forceLoad: Boolean = false): DataResponse<List<UIModel.WalletModel>>?
}