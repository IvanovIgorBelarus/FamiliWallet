package com.example.expenseobserver.features.newcategory.data

import com.example.common.CategoryType
import com.example.data.UIModel

object NewCategoryModel {

    private var model = UIModel.CategoryModel()

    private var isNewCategory: Boolean = false

    private var categoryType: CategoryType = com.example.common.CategoryType.INCOME

    fun getCategoryModel() = model

    fun isNewCategory() = isNewCategory

    fun getCategoryType() = categoryType

    fun setNewCategoryModel(model: UIModel.CategoryModel, isNewCategory: Boolean, categoryType: CategoryType) {
        NewCategoryModel.model = model
        this.isNewCategory = isNewCategory
        this.categoryType = categoryType
    }

    fun clearModel() {
        model = UIModel.CategoryModel()
    }
}