package com.example.familiwallet.features.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.familiwallet.core.common.TimeRangeType
import com.example.familiwallet.core.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private val uiState = mutableStateOf<UiState<TimeRangeType>>(UiState.Success(TimeRangeType.WEEK))

    fun getUiState(): State<UiState<TimeRangeType>> = uiState

    fun getUiInfo(timeRangeType: TimeRangeType) {
        viewModelScope.launch {
            uiState.value = UiState.Success(timeRangeType)
        }
    }
}