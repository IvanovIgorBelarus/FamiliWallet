package com.example.expenseobserver.features.walletscreen

import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.core.BaseViewModel
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UiState
import com.example.expenseobserver.features.start_screen.domain.usecase.WalletUseCase
import com.example.expenseobserver.features.walletscreen.data.WalletScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val walletUseCase: WalletUseCase
) : BaseViewModel<WalletScreenViewState>() {

    override fun getData(forceLoad: Boolean) {
        viewModelScope.launch {
            try {
                when (val walletsListResponse = walletUseCase.getWalletsList(forceLoad)) {
                    is DataResponse.Success -> uiState.value = UiState.Success(WalletScreenViewState(walletsListResponse.data))
                    is DataResponse.Error -> uiState.value = UiState.Error(walletsListResponse.exception)
                    else -> {}
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }

    fun deleteItem(item: UIModel.WalletModel) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            when (val response = walletUseCase.deleteWallet(item)) {
                is DataResponse.Success -> {
                    walletUseCase.getWalletsList(true)
                    getData(false)
                }
                is DataResponse.Error -> UiState.Error(response.exception)
            }
        }
    }
}