package com.example.expenseobserver.features.start_screen.domain.usecase

import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel

interface WalletUseCase {

    suspend fun getWalletsList(forceLoad: Boolean = false): DataResponse<List<UIModel.WalletModel>>?

    suspend fun updateWallet(wallet: UIModel.WalletModel): DataResponse<Unit>

    suspend fun addNewWallet(wallet: UIModel.WalletModel): DataResponse<Unit>

    suspend fun deleteWallet(wallet: UIModel.WalletModel): DataResponse<Unit>
}