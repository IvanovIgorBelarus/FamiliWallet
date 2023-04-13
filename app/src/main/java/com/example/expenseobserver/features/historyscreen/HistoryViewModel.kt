package com.example.expenseobserver.features.historyscreen

import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.App
import com.example.expenseobserver.core.BaseUseCase
import com.example.expenseobserver.core.BaseViewModel
import com.example.expenseobserver.core.common.CATEGORIES
import com.example.expenseobserver.core.common.INCOMES
import com.example.expenseobserver.core.common.TRANSACTIONS
import com.example.expenseobserver.core.common.TimeRangeType
import com.example.expenseobserver.core.common.WALLETS
import com.example.expenseobserver.core.common.currentDateFilter
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UiState
import com.example.expenseobserver.core.utils.toStartOfDay
import com.example.expenseobserver.features.historyscreen.data.HistoryViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor() : BaseViewModel<HistoryViewState, BaseUseCase>() {
    override fun getData(forceLoad: Boolean) {
        viewModelScope.launch {
            val categoriesList = getCategories(forceLoad)
            val transactionsList = getTransactions(forceLoad)
                .currentDateFilter()
                .sortedByDescending { it.date }

            uiState.value = UiState.Success(
                HistoryViewState(
                    categoriesList = categoriesList,
                    transactionsGroupList = transactionsList.groupBy { Date(it.date ?: 0).toStartOfDay.time }
                )
            )
        }
    }

    private suspend fun getCategories(forceLoad: Boolean) = (getItems(CATEGORIES, forceLoad) as? List<UIModel.CategoryModel>).orEmpty()

    private suspend fun getTransactions(forceLoad: Boolean) = (getItems(TRANSACTIONS, forceLoad) as? List<UIModel.TransactionModel>).orEmpty()

    fun changeTimeRange(timeRange: TimeRangeType) {
        uiState.value = UiState.Loading
        App.dateFilterType = timeRange
        getData()
    }

    fun deleteItem(item: UIModel.TransactionModel) {
        deleteItem(item) {
            getTransactions(true)
            updateWalletValue(
                walletId = item.moneyType,
                amount = item.value,
                needMinus = item.type == INCOMES
            ) {
                getData(false)
            }
        }
    }

    private suspend fun updateWalletValue(
        walletId: String?,
        amount: Double?,
        needMinus: Boolean,
        onSuccess: suspend () -> Unit
    ) {
        val walletsList = (getWallets() as? List<UIModel.WalletModel>).orEmpty()
        val wallet = walletsList.firstOrNull { it.id == walletId }
        wallet?.let {
            updateItem(
                wallet.apply {
                    value = if (needMinus) value?.minus(amount ?: 0.0) else value?.plus(amount ?: 0.0)
                },
                onSuccess = { onSuccess.invoke() })
        }
    }

    private suspend fun getWallets(forceLoad: Boolean = false) = getItems(WALLETS, forceLoad)
}