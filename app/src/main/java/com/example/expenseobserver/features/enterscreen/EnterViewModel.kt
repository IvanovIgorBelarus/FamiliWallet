package com.example.expenseobserver.features.enterscreen

import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.core.common.BaseViewModel
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.ui.UiState
import com.example.expenseobserver.features.updateversion.domain.UpdateAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterViewModel @Inject constructor(
    private val updateAppUseCase: UpdateAppUseCase
) : BaseViewModel<UIModel.UpdateModel>() {
    override fun getData(forceLoad: Boolean) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                when (val updateModel = updateAppUseCase.checkUpdates()) {
                    is DataResponse.Success -> uiState.value = UiState.Success(updateModel.data)
                    is DataResponse.Error -> UiState.Success(UIModel.UpdateModel())
                }
            } catch (e: Exception) {
                uiState.value = UiState.Success(UIModel.UpdateModel())
            }
        }
    }
}