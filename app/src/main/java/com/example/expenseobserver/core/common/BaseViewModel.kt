package com.example.expenseobserver.core.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.expenseobserver.core.ui.UiState

abstract class BaseViewModel<T> : ViewModel() {
    open val uiState: MutableState<UiState<T>> = mutableStateOf(UiState.Loading)

    fun getUiState(): State<UiState<T>> = uiState

    abstract fun getData(forceLoad: Boolean = false)

    var start: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                getData(field)
            }
        }
}