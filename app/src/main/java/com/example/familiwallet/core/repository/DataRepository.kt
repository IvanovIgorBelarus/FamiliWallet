package com.example.familiwallet.core.repository

import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel

interface DataRepository {
    suspend fun addPartner(accountModel: UIModel.AccountModel): DataResponse<Unit>
    suspend fun doTransaction(transactionModel: UIModel.TransactionModel): DataResponse<Unit>
    suspend fun doBakTransactions(transactionModel: UIModel.TransactionModel): DataResponse<Unit>
    suspend fun addNewCategory(categoryItem: UIModel.CategoryModel)
    suspend fun getSmsList(forceLoad: Boolean = false): DataResponse<List<UIModel.SmsModel>>?
    suspend fun getPartner(forceLoad: Boolean = false): DataResponse<UIModel.AccountModel>?
    suspend fun getTransactionsList(uid: String, forceLoad: Boolean = false): DataResponse<List<UIModel.TransactionModel>>?
    suspend fun getCategoriesList(uid: String, forceLoad: Boolean = false): DataResponse<List<UIModel.CategoryModel>>?
    suspend fun deleteItem(item: Any?)
    suspend fun upDateItem(item: Any?)
}