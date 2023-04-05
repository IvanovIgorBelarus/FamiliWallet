package com.example.expenseobserver.features.transacrionscreen

import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.core.BaseUseCase
import com.example.expenseobserver.core.BaseViewModel
import com.example.expenseobserver.core.common.CATEGORIES
import com.example.expenseobserver.core.common.EXPENSES
import com.example.expenseobserver.core.common.TRANSACTIONS
import com.example.expenseobserver.core.common.WALLETS
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor() : BaseViewModel<Unit, BaseUseCase>() {

    fun getTransactionDialogData(onSuccess: (List<UIModel.CategoryModel>, List<UIModel.WalletModel>) -> Unit) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val categoriesList = getCategories(false)
                val transactionsList = getTransactions(false)
                val walletsList = getWallets(false).sortedByDescending { it.isMainSource }

                if (uiState.value !is UiState.Error) {
                    onSuccess.invoke(
                        TransactionMapper.mapCategoryQueue(categoriesList, transactionsList),
                        walletsList
                    )
                    uiState.value = UiState.Success(Unit)
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }

    private suspend fun getCategories(forceLoad: Boolean) = (getItems(CATEGORIES, forceLoad) as? List<UIModel.CategoryModel>).orEmpty()

    private suspend fun getTransactions(forceLoad: Boolean) = (getItems(TRANSACTIONS, forceLoad) as? List<UIModel.TransactionModel>).orEmpty()

    private suspend fun getWallets(forceLoad: Boolean) = (getItems(WALLETS, forceLoad) as? List<UIModel.WalletModel>).orEmpty()


    fun addTransaction(transactionModel: UIModel.TransactionModel, onSuccess: () -> Unit = {}) {
        uiState.value = UiState.Loading
        addItem(transactionModel) {
            getTransactions(true)
            updateWalletValue(
                walletId = transactionModel.moneyType,
                amount = transactionModel.value,
                needMinus = transactionModel.type == EXPENSES,
                onSuccess = {
                    getWallets(true)
                    uiState.value = UiState.Success(Unit)
                    onSuccess.invoke()
                })
        }
    }

    private suspend fun updateWalletValue(
        walletId: String?,
        amount: Double?,
        needMinus: Boolean,
        onSuccess: suspend () -> Unit
    ) {
        val walletsList = (getWallets(false) as? List<UIModel.WalletModel>).orEmpty()
        val wallet = walletsList.firstOrNull { it.id == walletId }
        wallet?.let {
            updateItem(
                wallet.apply {
                    value = if (needMinus) value?.minus(amount ?: 0.0) else value?.plus(amount ?: 0.0)
                },
                onSuccess = { onSuccess.invoke() })
        }
    }


    override fun getData(forceLoad: Boolean) {
        uiState.value = UiState.Success(Unit)
    }
}