package com.alseda.start_impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.common.TimeRangeType
import com.example.common.mappers.TransactionMapper
import com.example.mylibrary.base.BaseUseCase
import com.example.mylibrary.base.BaseViewModel
import com.example.data.theme.UiState
import com.example.data.StartScreenViewState
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
            val startDate = transactionsList.last().date ?: TimeRangeType.MONTH.startDate
            val endDate = transactionsList.first().date ?: TimeRangeType.MONTH.endDate
            getData(TimeRangeType.MONTH.startDate < startDate || TimeRangeType.MONTH.endDate > endDate)
        }
    }

}