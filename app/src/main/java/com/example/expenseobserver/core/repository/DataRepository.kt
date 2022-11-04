package com.example.expenseobserver.core.repository

import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel

interface DataRepository {
    suspend fun addPartner(accountModel: UIModel.AccountModel): DataResponse<Unit>
    suspend fun doTransaction(transactionModel: UIModel.TransactionModel): DataResponse<Unit>
    suspend fun doBakTransactions(transactionModel: UIModel.TransactionModel): DataResponse<Unit>
    suspend fun addNewCategory(categoryItem: UIModel.CategoryModel): DataResponse<Unit>
    suspend fun getSmsList(forceLoad: Boolean = false): DataResponse<List<UIModel.SmsModel>>?
    suspend fun getPartner(forceLoad: Boolean = false): DataResponse<UIModel.AccountModel>?
    suspend fun getTransactionsList(forceLoad: Boolean = false): DataResponse<List<UIModel.TransactionModel>>?
    suspend fun getCategoriesList(forceLoad: Boolean = false): DataResponse<List<UIModel.CategoryModel>>?
    suspend fun deleteItem(item: Any?): DataResponse<Unit>
    suspend fun updateItem(item: Any?): DataResponse<Unit>
    suspend fun checkUpdates(): DataResponse<UIModel.UpdateModel>
}