package com.example.expenseobserver.core

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel<StateView, UseCase : BaseUseCase> : ViewModel() {

    val uiState: MutableState<UiState<StateView>> = mutableStateOf(UiState.Loading)

    @Inject
    lateinit var useCase: UseCase

    fun getUiState(): State<UiState<StateView>> = uiState

    abstract fun getData(forceLoad: Boolean = false)

    open fun clearData() {

    }

    fun addItem(item: UIModel, onSuccess: suspend CoroutineScope.() -> Unit = {}) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            when (val response = useCase.addItem(item)) {
                is DataResponse.Success -> {
                    onSuccess()
                }
                is DataResponse.Error -> uiState.value = UiState.Error(response.exception)
            }
        }
    }

    open fun deleteItem(item: UIModel, onSuccess: suspend CoroutineScope.() -> Unit = {}) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            when (val response = useCase.deleteItem(item)) {
                is DataResponse.Success -> {
                    onSuccess()
                }
                is DataResponse.Error -> uiState.value = UiState.Error(response.exception)
            }
        }
    }

    fun updateItem(item: UIModel, onSuccess: suspend CoroutineScope.() -> Unit = {}) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            when (val response = useCase.updateItem(item)) {
                is DataResponse.Success -> {
                    onSuccess()
                }
                is DataResponse.Error -> uiState.value = UiState.Error(response.exception)
            }
        }
    }

    suspend fun getItems(collectionName: String, forceLoad: Boolean = false): List<UIModel>? =
        viewModelScope.async {
            uiState.value = UiState.Loading
            return@async when (val response = useCase.getItems(collectionName, forceLoad)) {
                is DataResponse.Success -> {
                    response.data
                }
                is DataResponse.Error -> {
                    Log.e("MYNAME", response.exception.message.orEmpty())
                    uiState.value = UiState.Error(response.exception)
                    null
                }
                else -> null
            }
        }.await()

}