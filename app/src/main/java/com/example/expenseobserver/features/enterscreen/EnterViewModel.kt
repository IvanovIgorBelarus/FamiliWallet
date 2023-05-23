package com.example.expenseobserver.features.enterscreen

import androidx.lifecycle.viewModelScope
import com.example.data.UIModel
import com.example.mylibrary.base.BaseViewModel
import com.example.data.DataResponse
import com.example.data.theme.UiState
import com.example.expenseobserver.features.updateversion.domain.UpdateAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterViewModel @Inject constructor() : BaseViewModel<UIModel.UpdateModel, UpdateAppUseCase>() {
    override fun getData(forceLoad: Boolean) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                when (val updateModel = useCase.checkUpdates()) {
                    is DataResponse.Success -> uiState.value = UiState.Success(updateModel.data)
                    is DataResponse.Error -> UiState.Success(UIModel.UpdateModel())
                }
            } catch (e: Exception) {
                uiState.value = UiState.Success(UIModel.UpdateModel())
            }
        }
    }
}