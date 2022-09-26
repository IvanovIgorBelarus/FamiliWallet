package com.example.familiwallet.features.newcategory

import com.example.familiwallet.core.common.BaseViewModel
import com.example.familiwallet.core.data.AppIcons
import com.example.familiwallet.core.data.CategoryColor
import com.example.familiwallet.core.data.IconActionType
import com.example.familiwallet.core.ui.UiState
import com.example.familiwallet.features.newcategory.data.NewCategoryModel
import com.example.familiwallet.features.newcategory.data.NewCategoryViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewCategoryViewModel @Inject constructor() : BaseViewModel<NewCategoryViewState>() {

    override fun getData(forceLoad: Boolean) {
        uiState.value = UiState.Success(NewCategoryViewState(NewCategoryModel.getCategoryModel()))
        NewCategoryModel.clearModel()
    }

    fun getIcons(): List<Pair<IconActionType, List<AppIcons>>> {
        val resultList = mutableListOf<Pair<IconActionType, List<AppIcons>>>()
        IconActionType.values().forEach { type ->
            val typeList = AppIcons.values().filter { it.actionType == type }
            resultList.add(Pair(type, typeList))
        }
        return resultList
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