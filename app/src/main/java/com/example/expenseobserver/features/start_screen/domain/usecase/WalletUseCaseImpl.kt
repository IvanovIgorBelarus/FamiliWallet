package com.example.expenseobserver.features.start_screen.domain.usecase

import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.repository.DataRepository
import javax.inject.Inject

class WalletUseCaseImpl @Inject constructor(
    private val repo: DataRepository
) : WalletUseCase {
    override suspend fun getWalletsList(forceLoad: Boolean): DataResponse<List<UIModel.WalletModel>>? =
        repo.getWalletsList(forceLoad)

    override suspend fun updateWallet(wallet: UIModel.WalletModel): DataResponse<Unit> = repo.updateItem(wallet)

    override suspend fun addNewWallet(wallet: UIModel.WalletModel): DataResponse<Unit> = repo.addItem(wallet)

    override suspend fun deleteWallet(wallet: UIModel.WalletModel): DataResponse<Unit> = repo.deleteItem(wallet)
}