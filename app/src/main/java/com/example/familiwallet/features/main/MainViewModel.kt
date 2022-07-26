package com.example.familiwallet.features.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.familiwallet.core.common.EXPENSES
import com.example.familiwallet.core.common.INCOMES
import com.example.familiwallet.core.common.TimeRangeType
import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.ui.UiState
import com.example.familiwallet.features.main.domain.usecase.MainScreenInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainScreenInfoUseCase: MainScreenInfoUseCase
) : ViewModel() {

    private val uiRangeState = mutableStateOf<UiState<TimeRangeType>>(UiState.Success(TimeRangeType.WEEK))
    private val uiState = mutableStateOf<UiState<MainScreenViewState>>(UiState.Loading)

    init {
        getMainScreenInfo(TimeRangeType.MONTH)
    }

    fun getUiState(): State<UiState<MainScreenViewState>> = uiState

    fun getUiRangeState(): State<UiState<TimeRangeType>> = uiRangeState

    fun setUiRangeState(timeRangeType: TimeRangeType) {
        viewModelScope.launch {
            uiRangeState.value = UiState.Success(timeRangeType)
        }
    }

    private fun getMainScreenInfo(timeRangeType: TimeRangeType) {
        viewModelScope.launch {
            uiState.value = UiState.Loading

            try {
                val categoryListResponse = mainScreenInfoUseCase.getCategoriesList()
                var incomesList = listOf<UIModel.CategoryModel>()
                var expensesList = listOf<UIModel.CategoryModel>()
                when (categoryListResponse) {
                    is DataResponse.Success -> {
                        incomesList = categoryListResponse.data.filter { it.type == INCOMES }
                        expensesList = categoryListResponse.data.filter { it.type == EXPENSES }
                    }
                    is DataResponse.Error -> {

                    }
                }

                val transactionsListResponse = mainScreenInfoUseCase.getTransactionsList()
                var transactionsList = listOf<UIModel.TransactionModel>()
                when (transactionsListResponse) {
                    is DataResponse.Success -> {
                        transactionsList = transactionsListResponse.data
                    }
                    is DataResponse.Error -> {

                    }
                }

                uiState.value = UiState.Success(
                    MainScreenViewState(
                        incomesList = incomesList,
                        expensesList = expensesList,
                        transactionsList = transactionsList
                    )
                )
            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }
}