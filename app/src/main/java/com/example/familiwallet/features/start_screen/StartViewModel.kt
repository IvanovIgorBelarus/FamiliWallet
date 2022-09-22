package com.example.familiwallet.features.start_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.familiwallet.core.common.BaseViewModel
import com.example.familiwallet.core.common.TimeRangeType
import com.example.familiwallet.core.common.currentDateFilter
import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.ui.UiState
import com.example.familiwallet.features.main.domain.usecase.PartnerUseCase
import com.example.familiwallet.features.start_screen.domain.usecase.StartScreenInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val startScreenInfoUseCase: StartScreenInfoUseCase
) : BaseViewModel<StartScreenViewState>() {

    override fun getData(forceLoad: Boolean) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                val categoriesList = mutableListOf<UIModel.CategoryModel>()
                val transactionsList = mutableListOf<UIModel.TransactionModel>()
                when (val userData = getPersonData(forceLoad)) {
                    is DataResponse.Success -> {
                        categoriesList.addAll(userData.data.first)
                        transactionsList.addAll(userData.data.second)
                        uiState.value = UiState.Success(
                            StartScreenViewState(
                                categoriesList = categoriesList,
                                transactionsList = transactionsList.sortedByDescending { it.date }
                            )
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
            val categoryListResponse = startScreenInfoUseCase.getCategoriesList(forceLoad)
            val categoriesList = mutableListOf<UIModel.CategoryModel>()
            when (categoryListResponse) {
                is DataResponse.Success -> {
                    categoriesList.addAll(categoryListResponse.data)
                }
                is DataResponse.Error -> {
                    Log.w("ERROR", "categoryListResponse failed", categoryListResponse.exception)
                    return@async DataResponse.Error(categoryListResponse.exception)
                }
            }

            val transactionsListResponse = startScreenInfoUseCase.getTransactionsList(forceLoad)
            val transactionsList = mutableListOf<UIModel.TransactionModel>()
            when (transactionsListResponse) {
                is DataResponse.Success -> {
                    transactionsList.addAll(transactionsListResponse.data.currentDateFilter())
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

}