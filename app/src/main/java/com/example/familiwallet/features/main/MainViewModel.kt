package com.example.familiwallet.features.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.familiwallet.core.common.TimeRangeType
import com.example.familiwallet.core.repository.DataInteractor
import com.example.familiwallet.core.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val uiRangeState = mutableStateOf<UiState<TimeRangeType>>(UiState.Success(TimeRangeType.WEEK))
    private val uiState = mutableStateOf<UiState<MainScreenViewState>>(UiState.Loading)

    init {
        getMainScreenInfo(TimeRangeType.MONTH)
    }

    fun getUiState(): State<UiState<MainScreenViewState>> = uiState

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
                uiState.value = UiState.Success(
                    MainScreenViewState(categoryList = DataInteractor.getCategoriesList())
                )
            }catch (e:Exception){
                uiState.value = UiState.Error(e)
            }
        }
    }
}