package com.example.expenseobserver.features.start_screen

import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.App.Companion.dateFilterType
import com.example.mylibrary.base.BaseUseCase
import com.example.mylibrary.base.BaseViewModel
import com.example.data.theme.UiState
import com.example.data.StartScreenViewState
import com.example.expenseobserver.features.transacrionscreen.TransactionMapper
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

    private suspend fun getCategories(forceLoad: Boolean) = (getItems(com.example.common.CATEGORIES, forceLoad) as? List<com.example.data.UIModel.CategoryModel>).orEmpty()

    private suspend fun getTransactions(forceLoad: Boolean) = (getItems(com.example.common.TRANSACTIONS, forceLoad) as? List<com.example.data.UIModel.TransactionModel>).orEmpty()

    private suspend fun getWallets(forceLoad: Boolean) = (getItems(com.example.common.WALLETS, forceLoad) as? List<com.example.data.UIModel.WalletModel>).orEmpty()

    fun changeTimeRange(transactionsList: List<com.example.data.UIModel.TransactionModel>) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            val startDate = transactionsList.last().date ?: dateFilterType.startDate
            val endDate = transactionsList.first().date ?: dateFilterType.endDate
            getData(dateFilterType.startDate < startDate || dateFilterType.endDate > endDate)
        }
    }

}