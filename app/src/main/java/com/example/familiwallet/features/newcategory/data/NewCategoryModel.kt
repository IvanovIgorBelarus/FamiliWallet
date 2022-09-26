package com.example.familiwallet.features.newcategory.data

import com.example.familiwallet.core.data.UIModel

object NewCategoryModel {
    private var model = UIModel.CategoryModel()

    fun getCategoryModel() = model

    fun setNewCategoryModel(model:UIModel.CategoryModel) {
        NewCategoryModel.model = model
    }

    fun clearModel() {
        model = UIModel.CategoryModel()
    }
}