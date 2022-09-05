package com.example.familiwallet.core.repository

import com.example.familiwallet.core.common.BANK_MINUS
import com.example.familiwallet.core.common.CATEGORIES
import com.example.familiwallet.core.common.CATEGORY
import com.example.familiwallet.core.common.CURRENCY
import com.example.familiwallet.core.common.DATE
import com.example.familiwallet.core.common.ICON
import com.example.familiwallet.core.common.MONEY_TYPE
import com.example.familiwallet.core.common.NEW_SMS
import com.example.familiwallet.core.common.PARTNER_UID
import com.example.familiwallet.core.common.TRANSACTIONS
import com.example.familiwallet.core.common.TRANSACTION_TYPE
import com.example.familiwallet.core.common.UID
import com.example.familiwallet.core.common.USERS
import com.example.familiwallet.core.common.VALUE
import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.utils.UserUtils
import com.example.familiwallet.core.utils.toStartOfDay
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
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
                ICON to categoryItem.icon
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
                .whereEqualTo("uid", UserUtils.getUsersUid())
                .get()
                .addOnSuccessListener { response -> continuation.resume(DataResponse.Success(RepositoryMapper.mapPartner(response))) }
                .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
        } else {
            continuation.resume(DataResponse.Error(Throwable("Нет данных о партнёре")))
        }
    }

    suspend fun getPersonTransactionList(uid: DataResponse<UIModel.AccountModel>?): DataResponse<List<UIModel.TransactionModel>> = suspendCoroutine { continuation ->
        if(uid is DataResponse.Success) {
            val account = uid.data
            val firstDay = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -6) }.time.toStartOfDay.time
            db.collection(TRANSACTIONS)
                .whereEqualTo("uid", account.uid)
                .whereGreaterThanOrEqualTo("date",firstDay)
                .get()
                .addOnSuccessListener { response -> continuation.resume(DataResponse.Success(RepositoryMapper.mapPersonTransactionList(response))) }
                .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
        }
    }


    suspend fun getPersonCategoriesList(uid: String): DataResponse<List<UIModel.CategoryModel>> = suspendCoroutine { continuation ->
        if (uid.isNotEmpty()) {
            db.collection(CATEGORIES)
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener { response -> continuation.resume(DataResponse.Success(RepositoryMapper.getPersonCategoriesList(response))) }
                .addOnFailureListener { exception -> continuation.resume(DataResponse.Error(exception)) }
        } else {
            continuation.resume(DataResponse.Error(Throwable("Нет данных")))
        }
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
                    TRANSACTION_TYPE to item.type
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
}