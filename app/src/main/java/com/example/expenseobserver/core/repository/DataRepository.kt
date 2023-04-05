package com.example.expenseobserver.core.repository

import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel

interface DataRepository {
    suspend fun getPartner(forceLoad: Boolean = false): DataResponse<UIModel.AccountModel>?
    suspend fun addItem(item: UIModel?): DataResponse<Unit>
    suspend fun deleteItem(item: UIModel?): DataResponse<Unit>
    suspend fun updateItem(item: UIModel?): DataResponse<Unit>
    suspend fun getItems(collectionName:String, forceLoad: Boolean = false): DataResponse<List<UIModel>>?
    suspend fun checkUpdates(): DataResponse<UIModel.UpdateModel>
}