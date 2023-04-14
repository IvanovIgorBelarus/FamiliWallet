package com.example.expenseobserver.features.walletscreen

import android.os.Build
import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.core.BaseUseCase
import com.example.expenseobserver.core.BaseViewModel
import com.example.expenseobserver.core.common.CATEGORIES
import com.example.expenseobserver.core.common.TRANSACTIONS
import com.example.expenseobserver.core.common.TRANSFERS
import com.example.expenseobserver.core.common.WALLETS
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UiState
import com.example.expenseobserver.features.walletscreen.data.WalletScreenViewState
import com.example.expenseobserver.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor() : BaseViewModel<WalletScreenViewState, BaseUseCase>() {

    override fun getData(forceLoad: Boolean) {
        viewModelScope.launch {
            val wallet = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Screen.WalletScreen.args?.getParcelable("wallet", UIModel.WalletModel::class.java)
            } else {
                Screen.WalletScreen.args?.getParcelable("wallet")
            }
            val walletsList = getWallets(forceLoad)
            val transactions = getTransactions(forceLoad)
            val transfers = getTransfers(forceLoad)
            val categoriesList = getCategories(forceLoad)
            val operationsList = mutableListOf<UIModel>().apply {
                addAll(transactions)
                addAll(transfers)
            }
            uiState.value = UiState.Success(
                WalletScreenViewState(
                    walletList = walletsList.sortedByDescending { it.id == wallet?.id || it.isMainSource },
                    operationsList = operationsList,
                    categoriesList = categoriesList
                )
            )
        }
    }

    override fun deleteItem(item: UIModel, onSuccess: suspend CoroutineScope.() -> Unit) {
        super.deleteItem(item) {
            when (item) {
                is UIModel.WalletModel -> getWallets(true)
                is UIModel.TransactionModel -> getTransactions(true)
                is UIModel.TransferModel -> getTransfers(true)
                else ->{}
            }
            getData(false)
        }
    }

    fun getFilteredOperations(currentItem: UIModel.WalletModel, operationsList: List<UIModel>): List<UIModel> =
        operationsList.filter {
            when (it) {
                is UIModel.TransactionModel -> it.moneyType == currentItem.id
                is UIModel.TransferModel -> it.sourceId == currentItem.id || it.targetId == currentItem.id
                else -> false
            }
        }.sortedByDescending { (it as? UIModel.TransactionModel)?.date ?: (it as? UIModel.TransferModel)?.date ?: 0 }

    private suspend fun getWallets(forceLoad: Boolean) = (getItems(WALLETS, forceLoad) as? List<UIModel.WalletModel>).orEmpty()
    private suspend fun getTransactions(forceLoad: Boolean) = (getItems(TRANSACTIONS, forceLoad) as? List<UIModel.TransactionModel>).orEmpty()
    private suspend fun getCategories(forceLoad: Boolean) = (getItems(CATEGORIES, forceLoad) as? List<UIModel.CategoryModel>).orEmpty()
    private suspend fun getTransfers(forceLoad: Boolean) = (getItems(TRANSFERS, forceLoad) as? List<UIModel.TransactionModel>).orEmpty()
}