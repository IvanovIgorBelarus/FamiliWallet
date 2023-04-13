package com.example.expenseobserver.features.walletscreen

import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.core.BaseUseCase
import com.example.expenseobserver.core.BaseViewModel
import com.example.expenseobserver.core.common.WALLETS
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UiState
import com.example.expenseobserver.features.walletscreen.data.WalletScreenViewState
import com.example.expenseobserver.features.walletsettings.data.NewWalletModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor() : BaseViewModel<WalletScreenViewState, BaseUseCase>() {

    override fun getData(forceLoad: Boolean) {
        viewModelScope.launch {
            val walletsList = getWallets(forceLoad)
            uiState.value = UiState.Success(WalletScreenViewState(walletsList.sortedByDescending { it.isMainSource }))
        }
    }

    fun deleteItem(item: UIModel.WalletModel) {
        deleteItem(item) {
            getWallets(true)
            getData(false)
        }
    }

    fun openWalletSettings(item: UIModel.WalletModel? = null, openScreen: () -> Unit) {
        if (item != null) {
            NewWalletModel.setNewWalletModel(item)
        } else {
            NewWalletModel.clearModel()
        }
        openScreen.invoke()
    }

    private suspend fun getWallets(forceLoad: Boolean) = (getItems(WALLETS, forceLoad) as? List<UIModel.WalletModel>).orEmpty()
}