package com.example.expenseobserver.core.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.expenseobserver.core.data.UiState

abstract class BaseViewModel<T> : ViewModel() {
    open val uiState: MutableState<UiState<T>> = mutableStateOf(UiState.Loading)

    fun getUiState(): State<UiState<T>> = uiState

    abstract fun getData(forceLoad: Boolean = false)

    open fun clearData(){

    }
}