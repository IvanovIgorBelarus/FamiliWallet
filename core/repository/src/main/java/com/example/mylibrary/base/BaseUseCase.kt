package com.example.mylibrary.base

import com.example.data.DataResponse
import com.example.data.UIModel

interface BaseUseCase {

    suspend fun addItem(item: UIModel): DataResponse<Unit>

    suspend fun deleteItem(item: UIModel): DataResponse<Unit>

    suspend fun updateItem(item: UIModel): DataResponse<Unit>

    suspend fun getItems(collectionName:String, forceLoad: Boolean = false): DataResponse<List<UIModel>>?
}