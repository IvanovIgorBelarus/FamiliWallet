package com.example.expenseobserver.features.category

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.core.BaseViewModel
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UiState
import com.example.expenseobserver.features.category.domain.usecase.CategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor() : BaseViewModel<CategoryScreenViewState, CategoriesUseCase>() {

    override fun getData(forceLoad: Boolean) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                when (val categoryListResponse = useCase.getCategoriesList(forceLoad)) {
                    is DataResponse.Success -> {
                        uiState.value = UiState.Success(CategoryScreenViewState(categoryListResponse.data))
                    }
                    is DataResponse.Error -> {
                        Log.w("ERROR", "categoryListResponse failed", categoryListResponse.exception)
                        uiState.value = UiState.Error(categoryListResponse.exception)
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }

    fun deleteItem(item: UIModel.CategoryModel) {
        deleteItem(item) {
            useCase.getCategoriesList(true)
            getData(false)
        }
    }
}