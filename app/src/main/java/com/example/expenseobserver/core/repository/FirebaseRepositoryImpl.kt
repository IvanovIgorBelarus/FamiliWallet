package com.example.expenseobserver.core.repository

import com.example.expenseobserver.App.Companion.dateFilterType
import com.example.expenseobserver.core.common.CATEGORIES
import com.example.expenseobserver.core.common.DATE
import com.example.expenseobserver.core.common.NEW_SMS
import com.example.expenseobserver.core.common.TRANSACTIONS
import com.example.expenseobserver.core.common.UID
import com.example.expenseobserver.core.common.USERS
import com.example.expenseobserver.core.common.VERSION
import com.example.expenseobserver.core.common.WALLETS
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.utils.UserUtils
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseRepositoryImpl @Inject constructor() {

    private val db = FirebaseFirestore.getInstance()

    suspend fun getSmsList(): DataResponse<List<UIModel.SmsModel>> = suspendCoroutine { continuation ->
        db.collection(NEW_SMS).get()
            .addOnSuccessListener { response -> continuation.resume(DataResponse.Success(RepositoryMapper.mapSmsList(response))) }
            .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
    }


    suspend fun getPartner(): DataResponse<UIModel.AccountModel> = suspendCoroutine { continuation ->
        if (!UserUtils.getUsersUid().isNullOrEmpty()) {
            db.collection(USERS)
                .whereEqualTo(UID, UserUtils.getUsersUid())
                .get()
                .addOnSuccessListener { response -> continuation.resume(DataResponse.Success(RepositoryMapper.mapPartner(response))) }
                .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
        } else {
            continuation.resume(DataResponse.Error(Throwable("Нет данных о партнёре")))
        }
    }

    suspend fun getPersonTransactionList(partnerRequest: DataResponse<UIModel.AccountModel>?): DataResponse<List<UIModel.TransactionModel>> = suspendCoroutine { continuation ->
        val partnerUid = (partnerRequest as? DataResponse.Success)?.data?.partnerUid

        db.collection(TRANSACTIONS)
            .whereIn(UID, listOf(UserUtils.getUsersUid(), partnerUid))
            .whereGreaterThanOrEqualTo(FieldPath.of(DATE), dateFilterType.startDate)
            .whereLessThanOrEqualTo(FieldPath.of(DATE), dateFilterType.endDate)
            .get()
            .addOnSuccessListener { response -> continuation.resume(DataResponse.Success(RepositoryMapper.mapPersonTransactionList(response))) }
            .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
    }


    suspend fun getPersonCategoriesList(partnerRequest: DataResponse<UIModel.AccountModel>?): DataResponse<List<UIModel.CategoryModel>> = suspendCoroutine { continuation ->
        val partnerUid = (partnerRequest as? DataResponse.Success)?.data?.partnerUid

        db.collection(CATEGORIES)
            .whereIn(UID, listOf(UserUtils.getUsersUid(), partnerUid))
            .get()
            .addOnSuccessListener { response -> continuation.resume(DataResponse.Success(RepositoryMapper.getPersonCategoriesList(response))) }
            .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
    }

    suspend fun deleteItem(item: UIModel?): DataResponse<Unit> = suspendCoroutine { continuation ->
        val category = when (item) {
            is UIModel.CategoryModel -> CATEGORIES
            is UIModel.AccountModel -> USERS
            is UIModel.TransactionModel -> TRANSACTIONS
            is UIModel.SmsModel -> NEW_SMS
            is UIModel.WalletModel -> WALLETS
            else -> ""
        }
        db.collection(category)
            .document("${(item as UIModel.BaseModel).itemId}")
            .delete()
            .addOnSuccessListener { continuation.resume(DataResponse.Success(Unit)) }
            .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
    }

    suspend fun upDateItem(item: UIModel?): DataResponse<Unit> = suspendCoroutine { continuation ->
        val requestModel = RepositoryMapper.mapUpdateOrAddRequestInfo(item)

        if (requestModel is DataResponse.Success) {
            db.collection(requestModel.data.collectionPath)
                .document(requestModel.data.itemId)
                .update(requestModel.data.data)
                .addOnSuccessListener { continuation.resume(DataResponse.Success(Unit)) }
                .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
        } else {
            continuation.resume(requestModel as DataResponse.Error)
        }
    }

    suspend fun checkUpdates(): DataResponse<UIModel.UpdateModel> = suspendCoroutine { continuation ->
        db.collection(VERSION).get()
            .addOnSuccessListener { continuation.resume(DataResponse.Success(RepositoryMapper.getUpdateModel(it))) }
            .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
    }

    suspend fun addItem(item: UIModel?): DataResponse<Unit> = suspendCoroutine { continuation ->
        val requestModel = RepositoryMapper.mapUpdateOrAddRequestInfo(item)

        if (requestModel is DataResponse.Success) {
            db.collection(requestModel.data.collectionPath).add(
                requestModel.data.data
            )
                .addOnSuccessListener { continuation.resume(DataResponse.Success(Unit)) }
                .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
        } else {
            continuation.resume(requestModel as DataResponse.Error)
        }
    }

    suspend fun getWalletsList(partnerRequest: DataResponse<UIModel.AccountModel>?): DataResponse<List<UIModel.WalletModel>> = suspendCoroutine { continuation ->
        val partnerUid = (partnerRequest as? DataResponse.Success)?.data?.partnerUid

        db.collection(WALLETS)
            .whereIn(UID, listOf(UserUtils.getUsersUid(), partnerUid))
            .get()
            .addOnSuccessListener { response -> continuation.resume(DataResponse.Success(RepositoryMapper.getPersonWalletsList(response))) }
            .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
    }
}