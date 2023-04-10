package com.example.expenseobserver.features.transfer

import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.core.BaseUseCase
import com.example.expenseobserver.core.BaseViewModel
import com.example.expenseobserver.core.common.WALLETS
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UiState
import com.example.expenseobserver.features.transfer.data.TransferScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor() : BaseViewModel<TransferScreenViewState, BaseUseCase>() {

    override fun getData(forceLoad: Boolean) {}

    fun getTransferInfo(walletFromId: String?) {
        viewModelScope.launch {
            try {
                val walletsList = getWallets(false).sortedByDescending { it.isMainSource }
                val walletFrom = walletsList.first { it.id == walletFromId }
                uiState.value = UiState.Success(
                    TransferScreenViewState(
                        walletFrom = walletFrom,
                        walletsTo = walletsList.minus(walletFrom)
                    )
                )

            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }


    private suspend fun getWallets(forceLoad: Boolean) = (getItems(WALLETS, forceLoad) as? List<UIModel.WalletModel>).orEmpty()
}