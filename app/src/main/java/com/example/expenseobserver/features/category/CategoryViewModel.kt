package com.example.expenseobserver.features.category

import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.core.BaseUseCase
import com.example.expenseobserver.core.BaseViewModel
import com.example.expenseobserver.core.common.CATEGORIES
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor() : BaseViewModel<CategoryScreenViewState, BaseUseCase>() {

    override fun getData(forceLoad: Boolean) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            val categoriesList = (getItems(CATEGORIES, forceLoad) as? List<UIModel.CategoryModel>).orEmpty()
            uiState.value = UiState.Success(CategoryScreenViewState(categoriesList))
        }
    }

    fun deleteItem(item: UIModel.CategoryModel) {
        deleteItem(item) {
            getItems(CATEGORIES, true)
            getData(false)
        }
    }
}