package com.example.familiwallet.features.transacrionscreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.ui.UiState
import com.example.familiwallet.features.start_screen.domain.usecase.StartScreenInfoUseCase
import com.example.familiwallet.features.transacrionscreen.domain.TransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val startScreenInfoUseCase: StartScreenInfoUseCase,
    private val transactionUseCase: TransactionUseCase
) : ViewModel() {
    private val uiState = mutableStateOf<UiState<List<UIModel.CategoryModel>>>(UiState.Loading)

    fun getCategories(onSuccess: (List<UIModel.CategoryModel>) -> Unit) {
        viewModelScope.launch {
            try {
                when (val categoryListResponse = startScreenInfoUseCase.getCategoriesList()) {
                    is DataResponse.Success -> {
                        onSuccess.invoke(categoryListResponse.data)
                    }
                    is DataResponse.Error -> {
                        Log.w("ERROR", "categoryListResponse failed", categoryListResponse.exception)
                        uiState.value = UiState.Error(categoryListResponse.exception)
                    }
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }

    fun addTransaction(transactionModel: UIModel.TransactionModel) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                when (val response = transactionUseCase.doTransaction(transactionModel)) {
                    is DataResponse.Success -> {}
                    is DataResponse.Error ->  uiState.value = UiState.Error(response.exception)
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }
}