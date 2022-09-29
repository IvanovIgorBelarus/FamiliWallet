package com.example.familiwallet.features.category

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.familiwallet.core.common.BaseViewModel
import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.ui.UiState
import com.example.familiwallet.features.start_screen.domain.usecase.StartScreenInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val startScreenInfoUseCase: StartScreenInfoUseCase
) : BaseViewModel<CategoryScreenViewState>() {

    override fun getData(forceLoad: Boolean) {
        viewModelScope.launch {
            try {
                when (val categoryListResponse = startScreenInfoUseCase.getCategoriesList(forceLoad)) {
                    is DataResponse.Success -> {
                        uiState.value = UiState.Success(CategoryScreenViewState(categoryListResponse.data))
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
}