package com.example.expenseobserver.features.newcategory

import androidx.lifecycle.viewModelScope
import com.example.mylibrary.base.BaseUseCase
import com.example.mylibrary.base.BaseViewModel
import com.example.data.AppIcons
import com.example.data.CategoryColor
import com.example.data.IconActionType
import com.example.data.theme.UiState
import com.example.expenseobserver.features.newcategory.data.NewCategoryModel
import com.example.expenseobserver.features.newcategory.data.NewCategoryViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewCategoryViewModel @Inject constructor() : BaseViewModel<NewCategoryViewState, BaseUseCase>() {

    override fun getData(forceLoad: Boolean) {
        uiState.value = UiState.Success(NewCategoryViewState(NewCategoryModel.getCategoryModel()))
    }

    fun getIcons(): List<Pair<IconActionType, List<AppIcons>>> {
        val resultList = mutableListOf<Pair<IconActionType, List<AppIcons>>>()
        IconActionType.values().forEach { type ->
            if (type != IconActionType.UNKNOWN) {
                val typeList = AppIcons.values().filter { it.actionType == type }
                resultList.add(Pair(type, typeList))
            }
        }
        return resultList
    }

    fun sendCategoryRequest(
        category: String,
        icon: AppIcons,
        color: String,
        onSuccess: () -> Unit = {}
    ) {
        uiState.value = UiState.Loading
        val error = when {
            category.isEmpty() -> "Введите имя категории"
            icon == AppIcons.UNKNOWN || icon == AppIcons.PLUS -> "Выберите иконку для категории"
            else -> null
        }
        if (!error.isNullOrEmpty()) {
            uiState.value = UiState.Error(Exception(error))
            return
        }

        viewModelScope.launch {
            val request = com.example.data.UIModel.CategoryModel(
                uid = com.example.common.utils.UserUtils.getUsersUid(),
                category = category,
                type = NewCategoryModel.getCategoryType().type,
                icon = icon.name,
                color = color
            )
            try {
                if (NewCategoryModel.isNewCategory()) {
                    addNewCategory(request, onSuccess = onSuccess)
                } else {
                    updateCategory(request.apply { id = NewCategoryModel.getCategoryModel().itemId }, onSuccess = onSuccess)
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }

    private suspend fun updateCategory(
        item: com.example.data.UIModel.CategoryModel,
        onSuccess: () -> Unit = {}
    ) {
        updateItem(item) {
            getItems(com.example.common.CATEGORIES, true)
            onSuccess.invoke()
        }
    }

    private suspend fun addNewCategory(
        item: com.example.data.UIModel.CategoryModel,
        onSuccess: () -> Unit = {}
    ) {
        addItem(item) {
            getItems(com.example.common.CATEGORIES, true)
            onSuccess.invoke()
        }
    }

    fun getCategoriesColors() = listOf(
        CategoryColor.COLOR0,
        CategoryColor.COLOR1,
        CategoryColor.COLOR2,
        CategoryColor.COLOR3,
        CategoryColor.COLOR4,
        CategoryColor.COLOR5,
        CategoryColor.COLOR6,
        CategoryColor.COLOR7,
        CategoryColor.COLOR8,
        CategoryColor.COLOR9,
        CategoryColor.COLOR10,
        CategoryColor.COLOR11,
        CategoryColor.COLOR12,
        CategoryColor.COLOR13
    )
}