package com.example.expenseobserver.core.repository

import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel

interface DataRepository {
    suspend fun getSmsList(forceLoad: Boolean = false): DataResponse<List<UIModel.SmsModel>>?
    suspend fun getPartner(forceLoad: Boolean = false): DataResponse<UIModel.AccountModel>?
    suspend fun getTransactionsList(forceLoad: Boolean = false): DataResponse<List<UIModel.TransactionModel>>?
    suspend fun getCategoriesList(forceLoad: Boolean = false): DataResponse<List<UIModel.CategoryModel>>?
    suspend fun addItem(item: UIModel?): DataResponse<Unit>
    suspend fun deleteItem(item: UIModel?): DataResponse<Unit>
    suspend fun updateItem(item: UIModel?): DataResponse<Unit>
    suspend fun getItems(collectionName:String, forceLoad: Boolean = false)
    suspend fun checkUpdates(): DataResponse<UIModel.UpdateModel>
    suspend fun getWalletsList(forceLoad: Boolean = false): DataResponse<List<UIModel.WalletModel>>?
    suspend fun getTransfersList(forceLoad: Boolean = false): DataResponse<List<UIModel.WalletModel>>?
}