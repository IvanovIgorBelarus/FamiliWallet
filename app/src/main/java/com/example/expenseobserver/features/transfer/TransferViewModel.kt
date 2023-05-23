package com.example.expenseobserver.features.transfer

import androidx.lifecycle.viewModelScope
import com.example.mylibrary.base.BaseUseCase
import com.example.mylibrary.base.BaseViewModel
import com.example.common.WALLETS
import com.example.data.DataResponse
import com.example.data.UIModel
import com.example.data.theme.UiState
import com.example.common.utils.UserUtils
import com.example.expenseobserver.features.transfer.data.TransferScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.util.Date
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

    fun startTransfer(sourceId: String?, targetId: String?, amount: Double?, exchangeRate: Double?, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val walletsList = getWallets(false)
                val walletFrom = walletsList.firstOrNull { it.id == sourceId }
                val walletTo = walletsList.firstOrNull { it.id == targetId }

                val transferAmount = if (exchangeRate != null) {
                    amount ?: (0.0 * exchangeRate)
                } else {
                    amount
                }

                val walletFromRequest = async { walletFrom?.let { wallet -> useCase.updateItem(wallet.apply { value = value?.minus(transferAmount ?: 0.0) }) } }
                val walletToRequest = async { walletTo?.let { wallet -> useCase.updateItem(wallet.apply { value = value?.plus(transferAmount ?: 0.0) }) } }
                val transferRequest = async {
                    useCase.addItem(
                        UIModel.TransferModel(
                            uid = UserUtils.getUsersUid(),
                            sourceId = sourceId,
                            targetId = targetId,
                            date = Date().time,
                            value = transferAmount
                        )
                    )
                }

                val result = awaitAll(walletFromRequest, walletToRequest, transferRequest)
                val error = result.firstOrNull { it is DataResponse.Error }

                if (error == null) {
                    onSuccess.invoke()
                } else {
                    uiState.value = UiState.Error((error as? DataResponse.Error)?.exception)
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }
}