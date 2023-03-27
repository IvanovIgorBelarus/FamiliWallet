package com.example.expenseobserver.features.walletscreen

import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.core.common.BaseViewModel
import com.example.expenseobserver.core.data.DataResponse
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
}