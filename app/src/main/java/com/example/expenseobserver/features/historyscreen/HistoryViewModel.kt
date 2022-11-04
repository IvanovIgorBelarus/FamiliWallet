package com.example.expenseobserver.features.historyscreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.App
import com.example.expenseobserver.core.common.BaseViewModel
import com.example.expenseobserver.core.common.TimeRangeType
import com.example.expenseobserver.core.common.currentDateFilter
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.ui.UiState
import com.example.expenseobserver.core.utils.toStartOfDay
import com.example.expenseobserver.features.historyscreen.data.HistoryViewState
import com.example.expenseobserver.features.start_screen.domain.usecase.CategoriesUseCase
import com.example.expenseobserver.features.transacrionscreen.domain.TransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val categoriesUseCase: CategoriesUseCase,
    private val transactionUseCase: TransactionUseCase
) : BaseViewModel<HistoryViewState>() {
    override fun getData(forceLoad: Boolean) {
        viewModelScope.launch {
            try {
                val categoriesList = mutableListOf<UIModel.CategoryModel>()
                val transactionsList = mutableListOf<UIModel.TransactionModel>()
                when (val userData = getPersonData(forceLoad)) {
                    is DataResponse.Success -> {
                        categoriesList.addAll(userData.data.first)
                        transactionsList.addAll(userData.data.second)
                        uiState.value = UiState.Success(
                            HistoryViewState(
                                categoriesList = categoriesList,
                                transactionsGroupList = transactionsList.groupBy { Date(it.date ?: 0).toStartOfDay.time })
                        )
                    }
                    is DataResponse.Error -> {
                        uiState.value = UiState.Error(userData.exception)
                    }
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }

    private suspend fun getPersonData(forceLoad: Boolean) = viewModelScope.async {
        try {
            val categoryListResponse = categoriesUseCase.getCategoriesList(forceLoad)
            val categoriesList = mutableListOf<UIModel.CategoryModel>()
            when (categoryListResponse) {
                is DataResponse.Success -> {
                    categoriesList.addAll(categoryListResponse.data)
                }
                is DataResponse.Error -> {
                    Log.w("ERROR", "categoryListResponse failed", categoryListResponse.exception)
                    return@async DataResponse.Error(categoryListResponse.exception)
                }
                else -> {}
            }

            val transactionsListResponse = transactionUseCase.getTransactionsList(forceLoad)
            val transactionsList = mutableListOf<UIModel.TransactionModel>()
            when (transactionsListResponse) {
                is DataResponse.Success -> {
                    transactionsList.addAll(
                        transactionsListResponse.data
                            .currentDateFilter()
                            .sortedByDescending { it.date }
                    )
                }
                is DataResponse.Error -> {
                    Log.w("ERROR", "transactionsListResponse failed", transactionsListResponse.exception)
                    return@async DataResponse.Error(transactionsListResponse.exception)
                }
                else -> {}
            }
            return@async DataResponse.Success(Pair(categoriesList, transactionsList))
        } catch (e: Exception) {
            return@async DataResponse.Error(e)
        }
    }.await()

    fun changeTimeRange(timeRange: TimeRangeType){
        uiState.value = UiState.Loading
        App.dateFilterType = timeRange
        getData()
    }
}