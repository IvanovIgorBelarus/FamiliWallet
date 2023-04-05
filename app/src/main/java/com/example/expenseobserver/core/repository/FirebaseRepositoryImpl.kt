package com.example.expenseobserver.core.repository

import android.util.Log
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
        Log.e("MYNAME", "upDateItem request")
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
        Log.e("MYNAME", "addItem request")
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

    suspend fun getItems(partnerRequest: DataResponse<UIModel.AccountModel>?, collectionName: String): DataResponse<List<UIModel>> = suspendCoroutine { continuation ->
        val partnerUid = (partnerRequest as? DataResponse.Success)?.data?.partnerUid

        val request = if (collectionName != CATEGORIES && collectionName != WALLETS) {
            db.collection(collectionName)
                .whereIn(UID, listOf(UserUtils.getUsersUid(), partnerUid))
                .whereGreaterThanOrEqualTo(FieldPath.of(DATE), dateFilterType.startDate)
                .whereLessThanOrEqualTo(FieldPath.of(DATE), dateFilterType.endDate)
        } else {
            db.collection(collectionName)
                .whereIn(UID, listOf(UserUtils.getUsersUid(), partnerUid))
        }
        request.get()
            .addOnSuccessListener { response -> continuation.resume(DataResponse.Success(RepositoryMapper.mapItems(response, collectionName))) }
            .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
    }
}