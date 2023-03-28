package com.example.expenseobserver.features.transacrionscreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.core.BaseViewModel
import com.example.expenseobserver.core.common.currentDateFilter
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UiState
import com.example.expenseobserver.features.start_screen.domain.usecase.CategoriesUseCase
import com.example.expenseobserver.features.transacrionscreen.domain.TransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val categoriesUseCase: CategoriesUseCase,
    private val transactionUseCase: TransactionUseCase
) : BaseViewModel<Unit>() {

//    private val uiState = mutableStateOf<UiState<List<UIModel.CategoryModel>>>(UiState.Loading)

    fun getCategories(onSuccess: (List<UIModel.CategoryModel>) -> Unit) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                when (val categoryListResponse = categoriesUseCase.getCategoriesList()) {
                    is DataResponse.Success -> {
                        onSuccess.invoke(TransactionMapper.mapCategoryQueue(categoryListResponse.data, getTransactions()))
                        uiState.value = UiState.Success(Unit)
                    }
                    is DataResponse.Error -> {
                        Log.w("ERROR", "categoryListResponse failed", categoryListResponse.exception)
                        uiState.value = UiState.Error(categoryListResponse.exception)
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }

    fun addTransaction(transactionModel: UIModel.TransactionModel, onSuccess: () -> Unit= {}) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                when (val response = transactionUseCase.doTransaction(transactionModel)) {
                    is DataResponse.Success -> {
                        transactionUseCase.getTransactionsList(true)
                        onSuccess.invoke()
                        uiState.value = UiState.Success(Unit)
                    }
                    is DataResponse.Error -> uiState.value = UiState.Error(response.exception)
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }

    private suspend fun getTransactions() = viewModelScope.async {
        val transactionsListResponse = transactionUseCase.getTransactionsList()
        val transactionsList = mutableListOf<UIModel.TransactionModel>()
        when (transactionsListResponse) {
            is DataResponse.Success -> {
                transactionsList.addAll(transactionsListResponse.data.currentDateFilter())
            }
            is DataResponse.Error -> {
                Log.w("ERROR", "transactionsListResponse failed", transactionsListResponse.exception)
                return@async emptyList()
            }
            else -> {}
        }
        return@async transactionsList
    }.await()

    override fun getData(forceLoad: Boolean) {
        uiState.value = UiState.Success(Unit)
    }
}