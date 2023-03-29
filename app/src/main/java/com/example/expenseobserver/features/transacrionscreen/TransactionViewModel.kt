package com.example.expenseobserver.features.transacrionscreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.core.BaseViewModel
import com.example.expenseobserver.core.common.EXPENSES
import com.example.expenseobserver.core.common.currentDateFilter
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UiState
import com.example.expenseobserver.features.category.domain.usecase.CategoriesUseCase
import com.example.expenseobserver.features.transacrionscreen.domain.TransactionUseCase
import com.example.expenseobserver.features.walletscreen.domain.usecase.WalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val categoriesUseCase: CategoriesUseCase,
    private val walletUseCase: WalletUseCase,
) : BaseViewModel<Unit, TransactionUseCase>() {

    fun getTransactionDialogData(onSuccess: (List<UIModel.CategoryModel>, List<UIModel.WalletModel>) -> Unit) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val categoriesList = getCategories()
                val transactionsList = getTransactions()
                val walletsList = getWallets()

                if (uiState.value !is UiState.Error) {
                    onSuccess.invoke(
                        TransactionMapper.mapCategoryQueue(categoriesList, transactionsList),
                        walletsList
                    )
                    uiState.value = UiState.Success(Unit)
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }

    private suspend fun getCategories() = viewModelScope.async {
        try {
            val categoryListResponse = categoriesUseCase.getCategoriesList()
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

    private suspend fun getWallets(forceLoad: Boolean = false) = viewModelScope.async {
        try {
            val walletResponse = walletUseCase.getWalletsList(forceLoad)
            val walletsList = mutableListOf<UIModel.WalletModel>()

            when (walletResponse) {
                is DataResponse.Success -> {
                    walletsList.addAll(walletResponse.data)
                }
                is DataResponse.Error -> {
                    Log.w("ERROR", "categoryListResponse failed", walletResponse.exception)
                    uiState.value = UiState.Error(walletResponse.exception)
                    return@async emptyList()
                }
                else -> {}
            }
            return@async walletsList
        } catch (e: Exception) {
            uiState.value = UiState.Error(e)
            return@async emptyList()
        }
    }.await()

    fun addTransaction(transactionModel: UIModel.TransactionModel, onSuccess: () -> Unit = {}) {
        uiState.value = UiState.Loading
        addItem(transactionModel) {
            useCase.getTransactionsList(true)
            updateWalletValue(
                walletId = transactionModel.moneyType,
                amount = transactionModel.value,
                needMinus = transactionModel.type == EXPENSES,
                onSuccess = {
                    getWallets(true)
                    uiState.value = UiState.Success(Unit)
                    onSuccess.invoke()
                })
        }
    }

    private suspend fun getTransactions() = viewModelScope.async {
        val transactionsListResponse = useCase.getTransactionsList()
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

    private suspend fun updateWalletValue(
        walletId: String?,
        amount: Double?,
        needMinus: Boolean,
        onSuccess: suspend () -> Unit
    ) {
        val walletsList = getWallets()
        val wallet = walletsList.firstOrNull { it.id == walletId }
        wallet?.let {
            updateItem(
                wallet.apply {
                    value = if (needMinus) value?.minus(amount ?: 0.0) else value?.plus(amount ?: 0.0)
                },
                onSuccess = { onSuccess.invoke() })
        }
    }


    override fun getData(forceLoad: Boolean) {
        uiState.value = UiState.Success(Unit)
    }
}