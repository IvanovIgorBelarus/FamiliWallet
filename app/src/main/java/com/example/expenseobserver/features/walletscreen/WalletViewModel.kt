package com.example.expenseobserver.features.walletscreen

import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.core.BaseViewModel
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UiState
import com.example.expenseobserver.features.walletscreen.data.WalletScreenViewState
import com.example.expenseobserver.features.walletscreen.domain.usecase.WalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor() : BaseViewModel<WalletScreenViewState, WalletUseCase>() {

    override fun getData(forceLoad: Boolean) {
        viewModelScope.launch {
            try {
                when (val walletsListResponse = useCase.getWalletsList(forceLoad)) {
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
        deleteItem(item) {
            useCase.getWalletsList(true)
            getData(false)
        }
    }
}