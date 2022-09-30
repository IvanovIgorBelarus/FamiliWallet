package com.example.familiwallet.features.start_screen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.familiwallet.core.common.BaseViewModel
import com.example.familiwallet.core.common.currentDateFilter
import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.ui.UiState
import com.example.familiwallet.features.start_screen.data.StartScreenViewState
import com.example.familiwallet.features.start_screen.domain.usecase.CategoriesUseCase
import com.example.familiwallet.features.transacrionscreen.domain.TransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val categoriesUseCase: CategoriesUseCase,
    private val transactionUseCase: TransactionUseCase
) : BaseViewModel<StartScreenViewState>() {

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

    fun deleteItem(item: UIModel.TransactionModel) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            when (val response = transactionUseCase.deleteTransaction(item)) {
                is DataResponse.Success -> {
                    transactionUseCase.getTransactionsList(true)
                    getData(false)
                }
                is DataResponse.Error -> UiState.Error(response.exception)
            }
        }
    }

}