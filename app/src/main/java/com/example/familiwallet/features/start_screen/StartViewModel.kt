package com.example.familiwallet.features.start_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.familiwallet.core.common.EXPENSES
import com.example.familiwallet.core.common.INCOMES
import com.example.familiwallet.core.common.TimeRangeType
import com.example.familiwallet.core.common.currentDateFilter
import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.ui.UiState
import com.example.familiwallet.features.start_screen.domain.usecase.StartScreenInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val startScreenInfoUseCase: StartScreenInfoUseCase
) : ViewModel() {

    private val uiRangeState = mutableStateOf<UiState<TimeRangeType>>(UiState.Success(TimeRangeType.WEEK))
    private val uiState = mutableStateOf<UiState<StartScreenViewState>>(UiState.Loading)

    init {
        getMainScreenInfo(TimeRangeType.MONTH)
    }

    fun getUiState(): State<UiState<StartScreenViewState>> = uiState

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
                val categoryListResponse = startScreenInfoUseCase.getCategoriesList()
                var categoriesList = listOf<UIModel.CategoryModel>()
                when (categoryListResponse) {
                    is DataResponse.Success -> {
                        categoriesList = categoryListResponse.data
                    }
                    is DataResponse.Error -> {

                    }
                }

                val transactionsListResponse = startScreenInfoUseCase.getTransactionsList()
                var transactionsList = listOf<UIModel.TransactionModel>()
                when (transactionsListResponse) {
                    is DataResponse.Success -> {
                        transactionsList = transactionsListResponse.data.currentDateFilter().sortedByDescending { it.date }
                    }
                    is DataResponse.Error -> {

                    }
                    else->{}
                }

                uiState.value = UiState.Success(
                    StartScreenViewState(
                        categoriesList = categoriesList,
                        transactionsList = transactionsList
                    )
                )
            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }
}