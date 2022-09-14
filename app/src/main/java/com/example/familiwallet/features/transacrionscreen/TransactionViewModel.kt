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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val startScreenInfoUseCase: StartScreenInfoUseCase
) : ViewModel() {
    private val uiState = mutableStateOf<UiState<List<UIModel.CategoryModel>>>(UiState.Loading)

    fun getUiState(): State<UiState<List<UIModel.CategoryModel>>> = uiState

    fun getCategories() {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                when (val categoryListResponse = startScreenInfoUseCase.getCategoriesList()) {
                    is DataResponse.Success -> {
                        uiState.value = UiState.Success(categoryListResponse.data)
                    }
                    is DataResponse.Error -> {
                        Log.w("ERROR", "categoryListResponse failed", categoryListResponse.exception)
                        DataResponse.Error(categoryListResponse.exception)
                    }
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }
}