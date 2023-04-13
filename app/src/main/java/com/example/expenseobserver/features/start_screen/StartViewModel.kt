package com.example.expenseobserver.features.start_screen

import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.App.Companion.dateFilterType
import com.example.expenseobserver.core.BaseUseCase
import com.example.expenseobserver.core.BaseViewModel
import com.example.expenseobserver.core.common.CATEGORIES
import com.example.expenseobserver.core.common.TRANSACTIONS
import com.example.expenseobserver.core.common.TransactionMapper
import com.example.expenseobserver.core.common.WALLETS
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UiState
import com.example.expenseobserver.features.start_screen.data.StartScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor() : BaseViewModel<StartScreenViewState, BaseUseCase>() {

    override fun getData(forceLoad: Boolean) {
        viewModelScope.launch {
            try {
                val categoriesList = getCategories(forceLoad)
                val transactionsList = getTransactions(forceLoad)
                val walletsList = getWallets(forceLoad).sortedByDescending { it.isMainSource }
                if (uiState.value !is UiState.Error) {
                    uiState.value = UiState.Success(
                        StartScreenViewState(
                            categoriesList = categoriesList,
                            transactionsList = transactionsList.sortedByDescending { it.date },
                            summaryTransactionMap = TransactionMapper.mapSummaryTransactionList(categoriesList, transactionsList).groupBy { it.type },
                            walletList = walletsList
                        )
                    )
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }

    private suspend fun getCategories(forceLoad: Boolean) = (getItems(CATEGORIES, forceLoad) as? List<UIModel.CategoryModel>).orEmpty()

    private suspend fun getTransactions(forceLoad: Boolean) = (getItems(TRANSACTIONS, forceLoad) as? List<UIModel.TransactionModel>).orEmpty()

    private suspend fun getWallets(forceLoad: Boolean) = (getItems(WALLETS, forceLoad) as? List<UIModel.WalletModel>).orEmpty()

    fun changeTimeRange(transactionsList: List<UIModel.TransactionModel>) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            val startDate = transactionsList.last().date ?: dateFilterType.startDate
            val endDate = transactionsList.first().date ?: dateFilterType.endDate
            getData(dateFilterType.startDate < startDate || dateFilterType.endDate > endDate)
        }
    }

}