package com.example.expenseobserver.features.start_screen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.App.Companion.dateFilterType
import com.example.expenseobserver.core.BaseViewModel
import com.example.expenseobserver.core.common.TransactionMapper
import com.example.expenseobserver.core.common.currentDateFilter
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UiState
import com.example.expenseobserver.features.start_screen.data.StartScreenViewState
import com.example.expenseobserver.features.category.domain.usecase.CategoriesUseCase
import com.example.expenseobserver.features.walletscreen.domain.usecase.WalletUseCase
import com.example.expenseobserver.features.transacrionscreen.domain.TransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
    private val walletUseCase: WalletUseCase
) : BaseViewModel<StartScreenViewState, CategoriesUseCase>() {

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

    private suspend fun getCategories(forceLoad: Boolean) = viewModelScope.async {
        try {
            val categoryListResponse = useCase.getCategoriesList(forceLoad)
            val categoriesList = mutableListOf<UIModel.CategoryModel>()

            when (categoryListResponse) {
                is DataResponse.Success -> {
                    categoriesList.addAll(categoryListResponse.data)
                }
                is DataResponse.Error -> {
                    Log.w("ERROR", "categoryListResponse failed", categoryListResponse.exception)
                    uiState.value = UiState.Error(categoryListResponse.exception)
                    return@async emptyList()
                }
                else -> {}
            }
            return@async categoriesList
        } catch (e: Exception) {
            uiState.value = UiState.Error(e)
            return@async emptyList()
        }
    }.await()

    private suspend fun getTransactions(forceLoad: Boolean) = viewModelScope.async {
        try {
            val transactionsListResponse = transactionUseCase.getTransactionsList(forceLoad)
            val transactionsList = mutableListOf<UIModel.TransactionModel>()

            when (transactionsListResponse) {
                is DataResponse.Success -> {
                    transactionsList.addAll(transactionsListResponse.data.currentDateFilter())
                }
                is DataResponse.Error -> {
                    Log.w("ERROR", "transactionsListResponse failed", transactionsListResponse.exception)
                    uiState.value = UiState.Error(transactionsListResponse.exception)
                    return@async emptyList()
                }
                else -> {}
            }
            return@async transactionsList
        } catch (e: Exception) {
            uiState.value = UiState.Error(e)
            return@async emptyList()
        }
    }.await()

    private suspend fun getWallets(forceLoad: Boolean) = viewModelScope.async {
        try {
            val walletsListResponse = walletUseCase.getWalletsList(forceLoad)
            val walletList = mutableListOf<UIModel.WalletModel>()
            when (walletsListResponse) {
                is DataResponse.Success -> {
                    walletList.addAll(walletsListResponse.data)
                }
                is DataResponse.Error -> {
                    Log.w("ERROR", "walletsListResponse failed", walletsListResponse.exception)
                    uiState.value = UiState.Error(walletsListResponse.exception)
                    return@async emptyList()
                }
                else -> {}
            }
            return@async walletList
        } catch (e: Exception) {
            uiState.value = UiState.Error(e)
            return@async emptyList()
        }
    }.await()

    fun changeTimeRange(transactionsList: List<UIModel.TransactionModel>) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            val startDate = transactionsList.last().date ?: dateFilterType.startDate
            val endDate = transactionsList.first().date ?: dateFilterType.endDate
            getData(dateFilterType.startDate < startDate || dateFilterType.endDate > endDate)
        }
    }

}