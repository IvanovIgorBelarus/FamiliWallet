package com.example.expenseobserver.features.newcategory

import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.core.common.BaseViewModel
import com.example.expenseobserver.core.data.AppIcons
import com.example.expenseobserver.core.data.CategoryColor
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.IconActionType
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UiState
import com.example.expenseobserver.core.utils.UserUtils
import com.example.expenseobserver.features.newcategory.data.NewCategoryModel
import com.example.expenseobserver.features.newcategory.data.NewCategoryViewState
import com.example.expenseobserver.features.start_screen.domain.usecase.CategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewCategoryViewModel @Inject constructor(
    private val categoriesUseCase: CategoriesUseCase
) : BaseViewModel<NewCategoryViewState>() {

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
        color: CategoryColor,
        onSuccess: () -> Unit = {}
    ) {
        uiState.value = UiState.Loading
        val error = when {
            category.isEmpty() -> "Введите имя категории"
            icon == AppIcons.UNKNOWN||icon == AppIcons.PLUS -> "Выберите иконку для категории"
            else -> null
        }
        if (!error.isNullOrEmpty()) {
            uiState.value = UiState.Error(Exception(error))
            return
        }

        viewModelScope.launch {
            val request = UIModel.CategoryModel(
                uid = UserUtils.getUsersUid(),
                category = category,
                type = NewCategoryModel.getCategoryType().type,
                icon = icon.name,
                color = color.name
            )
            try {
                val response = if (NewCategoryModel.isNewCategory()) {
                    addNewCategory(request)
                } else {
                    updateCategory(request.apply { id = NewCategoryModel.getCategoryModel().itemId })
                }

                when (response) {
                    is DataResponse.Success -> {
                        categoriesUseCase.getCategoriesList(true)
                        onSuccess.invoke()
                    }
                    is DataResponse.Error -> {
                        uiState.value = UiState.Error(response.exception)
                    }
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }

    private suspend fun updateCategory(item: UIModel.CategoryModel) =
        withContext(viewModelScope.coroutineContext) { categoriesUseCase.updateCategory(item) }

    private suspend fun addNewCategory(item: UIModel.CategoryModel) =
        withContext(viewModelScope.coroutineContext) { categoriesUseCase.addNewCategory(item) }

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