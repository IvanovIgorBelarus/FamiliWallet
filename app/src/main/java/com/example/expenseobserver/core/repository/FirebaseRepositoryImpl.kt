package com.example.expenseobserver.core.repository

import com.example.expenseobserver.core.common.BANK_MINUS
import com.example.expenseobserver.core.common.CATEGORIES
import com.example.expenseobserver.core.common.CATEGORY
import com.example.expenseobserver.core.common.COLOR
import com.example.expenseobserver.core.common.CURRENCY
import com.example.expenseobserver.core.common.DATE
import com.example.expenseobserver.core.common.ICON
import com.example.expenseobserver.core.common.MONEY_TYPE
import com.example.expenseobserver.core.common.NEW_SMS
import com.example.expenseobserver.core.common.PARTNER_UID
import com.example.expenseobserver.core.common.TRANSACTIONS
import com.example.expenseobserver.core.common.TRANSACTION_TYPE
import com.example.expenseobserver.core.common.TimeRangeType
import com.example.expenseobserver.core.common.UID
import com.example.expenseobserver.core.common.USERS
import com.example.expenseobserver.core.common.VALUE
import com.example.expenseobserver.core.common.VERSION
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

    suspend fun addPartner(accountModel: UIModel.AccountModel): DataResponse<Unit> = suspendCoroutine { continuation ->
        db.collection(USERS).add(
            mapOf(
                UID to accountModel.uid,
                PARTNER_UID to accountModel.partnerUid
            )
        )
            .addOnSuccessListener { continuation.resume(DataResponse.Success(Unit)) }
            .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
    }

    suspend fun doTransaction(transactionModel: UIModel.TransactionModel): DataResponse<Unit> = suspendCoroutine { continuation ->
        db.collection(TRANSACTIONS).add(
            mapOf(
                UID to transactionModel.uid,
                TRANSACTION_TYPE to transactionModel.type,
                CATEGORY to transactionModel.category,
                CURRENCY to transactionModel.currency,
                MONEY_TYPE to transactionModel.moneyType,
                VALUE to transactionModel.value,
                DATE to transactionModel.date
            )
        )
            .addOnSuccessListener { continuation.resume(DataResponse.Success(Unit)) }
            .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
    }

    suspend fun doBakTransactions(transactionModel: UIModel.TransactionModel): DataResponse<Unit> = suspendCoroutine { continuation ->
        val value = if (transactionModel.moneyType == BANK_MINUS) {
            -transactionModel.value!!
        } else {
            transactionModel.value!!
        }
        db.collection(TRANSACTIONS).add(
            mapOf(
                UID to transactionModel.uid,
                TRANSACTION_TYPE to transactionModel.type,
                CURRENCY to transactionModel.currency,
                MONEY_TYPE to "копилка",
                VALUE to value,
                DATE to transactionModel.date
            )
        )
            .addOnSuccessListener { continuation.resume(DataResponse.Success(Unit)) }
            .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
    }

    suspend fun addNewCategory(categoryItem: UIModel.CategoryModel): DataResponse<Unit> = suspendCoroutine { continuation ->
        db.collection(CATEGORIES).add(
            mapOf(
                UID to categoryItem.uid,
                CATEGORY to categoryItem.category,
                TRANSACTION_TYPE to categoryItem.type,
                ICON to categoryItem.icon,
                COLOR to categoryItem.color
            )
        )
            .addOnSuccessListener { continuation.resume(DataResponse.Success(Unit)) }
            .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
    }

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
            .whereGreaterThanOrEqualTo(FieldPath.of(DATE), TimeRangeType.MONTH.startDate)
            .whereLessThanOrEqualTo(FieldPath.of(DATE), TimeRangeType.MONTH.endDate)
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

    suspend fun deleteItem(item: Any?): DataResponse<Unit> = suspendCoroutine { continuation ->
        val category = when (item) {
            is UIModel.CategoryModel -> CATEGORIES
            is UIModel.AccountModel -> USERS
            is UIModel.TransactionModel -> TRANSACTIONS
            is UIModel.SmsModel -> NEW_SMS
            else -> ""
        }
        db.collection(category)
            .document("${(item as UIModel.BaseModel).itemId}")
            .delete()
            .addOnSuccessListener { continuation.resume(DataResponse.Success(Unit)) }
            .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
    }

    suspend fun upDateItem(item: Any?): DataResponse<Unit> = suspendCoroutine { continuation ->
        var collectionPath = ""
        var data = mapOf<String, Any?>()
        var itemId = ""
        when (item) {
            is UIModel.CategoryModel -> {
                collectionPath = CATEGORIES
                itemId = item.id.orEmpty()
                data = mapOf(
                    UID to item.uid,
                    CATEGORY to item.category,
                    ICON to item.icon,
                    TRANSACTION_TYPE to item.type,
                    COLOR to item.color
                )
            }
            is UIModel.AccountModel -> {
                collectionPath = USERS
                itemId = item.id.orEmpty()
                data = mapOf(
                    UID to item.uid,
                    PARTNER_UID to item.partnerUid
                )
            }
            is UIModel.TransactionModel -> {
                collectionPath = TRANSACTIONS
                itemId = item.id.orEmpty()
                data = mapOf(
                    UID to item.uid,
                    TRANSACTION_TYPE to item.type,
                    CATEGORY to item.category,
                    CURRENCY to item.currency,
                    MONEY_TYPE to item.moneyType,
                    VALUE to item.value,
                    DATE to item.date
                )
            }
            else -> continuation.resume(DataResponse.Error(Throwable("не удалось обновить запись")))
        }
        db.collection(collectionPath).document(itemId).update(data)
            .addOnSuccessListener { continuation.resume(DataResponse.Success(Unit)) }
            .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
    }

    suspend fun checkUpdates(): DataResponse<UIModel.UpdateModel> = suspendCoroutine { continuation ->
        db.collection(VERSION).get()
            .addOnSuccessListener { continuation.resume(DataResponse.Success(RepositoryMapper.getUpdateModel(it))) }
            .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
    }
}