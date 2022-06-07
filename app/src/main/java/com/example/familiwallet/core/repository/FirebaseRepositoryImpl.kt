package com.example.familiwallet.core.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import com.example.familiwallet.core.common.*
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.utils.UserUtils
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseRepositoryImpl(private val db: FirebaseFirestore) {

    fun addPartner(accountModel: UIModel.AccountModel) {
        db.collection(USERS).add(
            mapOf(
                UID to accountModel.uid,
                PARTNER_UID to accountModel.partnerUid
            )
        )
    }

    fun doTransaction(transactionModel: UIModel.TransactionModel, isSms: Boolean) {
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
        ).addOnSuccessListener {
            CoroutineScope(Dispatchers.IO).launch {
                if (isSms) {
                    deleteItem(UIModel.SmsModel(id = transactionModel.id))
                }
//                DataInteractor.update(transactionModel)
            }

        }
    }

    fun doBakTransactions(transactionModel: UIModel.TransactionModel) {
        var value = if (transactionModel.moneyType == BANK_MINUS) {
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
        ).addOnSuccessListener {
            CoroutineScope(Dispatchers.IO).launch {
//                DataInteractor.update(transactionModel)
            }
        }
    }

    fun addNewCategory(categoryItem: UIModel.CategoryModel) {
        db.collection(CATEGORIES).add(
            mapOf(
                UID to categoryItem.uid,
                CATEGORY to categoryItem.category,
                TRANSACTION_TYPE to categoryItem.type,
                ICON to categoryItem.icon
            )
        ).addOnSuccessListener { CoroutineScope(Dispatchers.IO).launch {
//            DataInteractor.update(categoryItem)
        }
        }
    }

    suspend fun getSmsList(): List<UIModel.SmsModel> = suspendCoroutine { continuation ->
        val list = mutableListOf<UIModel.SmsModel>()
        db.collection(NEW_SMS).get().addOnSuccessListener { result ->
            result.forEach { doc ->
                list.add(
                    UIModel.SmsModel(
                        id = doc.id,
                        date = doc.getLong(DATE),
                        value = doc.getDouble(VALUE),
                        currency = doc.getString(CURRENCY),
                    )
                )
            }
            continuation.resume(list)
        }
    }


    suspend fun getPartner(): UIModel.AccountModel = suspendCoroutine { continuation ->
        val partner = UIModel.AccountModel()
        db.collection(USERS).get().addOnSuccessListener { result ->
            result.forEach { doc ->
                if (doc.getString(UID) == UserUtils.getUsersUid() && doc.getString(PARTNER_UID) != null) {
                    partner.id = doc.id
                    partner.uid = UserUtils.getUsersUid()
                    partner.partnerUid = doc.getString(PARTNER_UID)
                    return@forEach
                } else if (doc.getString(UID) == UserUtils.getUsersUid()) {
                    partner.id = doc.id
                    partner.uid = UserUtils.getUsersUid()
                    partner.partnerUid = doc.getString(PARTNER_UID)
                }
            }
            continuation.resume(partner)
        }
    }

    suspend fun getTransactionsList(): List<UIModel.TransactionModel> = suspendCoroutine { continuation ->
        val list = mutableListOf<UIModel.TransactionModel>()
        db.collection(TRANSACTIONS).get().addOnSuccessListener { result ->
            result.forEach { doc ->
                list.add(
                    UIModel.TransactionModel(
                        id = doc.id,
                        uid = doc.getString(UID),
                        type = doc.getString(TRANSACTION_TYPE),
                        category = doc.getString(CATEGORY),
                        currency = doc.getString(CURRENCY),
                        moneyType = doc.getString(MONEY_TYPE),
                        value = doc.getDouble(VALUE),
                        date = doc.getLong(DATE)
                    )
                )
            }
            continuation.resume(list)
        }
    }

    suspend fun getCategoriesList(partner: UIModel.AccountModel): List<UIModel.CategoryModel> = withContext(Dispatchers.IO) {
        val list = mutableListOf<UIModel.CategoryModel>()
        val getUserList = async { getPersonCategoriesList(partner.uid.orEmpty()) }
        val getPartnerList = async { getPersonCategoriesList(partner.partnerUid.orEmpty()) }

        val userList = getUserList.await()
        val partnerList = getPartnerList.await()
        list.addAll(userList)
        list.addAll(partnerList)

        list.sortBy { it.type == EXPENSES }
        list
    }

    private suspend fun getPersonCategoriesList(uid: String): List<UIModel.CategoryModel> = suspendCoroutine { continuation ->
        if (uid.isNotEmpty()) {
            db.collection(CATEGORIES)
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener { result ->
                    val list = mutableListOf<UIModel.CategoryModel>()
                    result.forEach { doc ->
                        list.add(
                            UIModel.CategoryModel(
                                id = doc.id,
                                uid = doc.getString(UID),
                                category = doc.getString(CATEGORY),
                                type = doc.getString(TRANSACTION_TYPE),
                                icon = doc.getString(ICON) ?: Icons.Default.List.name
                            )
                        )
                    }
                    continuation.resume(list)
                }
                .addOnFailureListener { continuation.resume(emptyList()) }
        }else{
            continuation.resume(emptyList())
        }
    }


    fun deleteItem(item: Any?) {
        var category = when (item) {
            is UIModel.CategoryModel -> CATEGORIES
            is UIModel.AccountModel -> USERS
            is UIModel.TransactionModel -> TRANSACTIONS
            is UIModel.SmsModel -> NEW_SMS
            else -> ""
        }
        db.collection(category)
            .document("${(item as UIModel.BaseModel).itemId}")
            .delete()
            .addOnSuccessListener { CoroutineScope(Dispatchers.IO).launch {
//                DataInteractor.update(item)
            } }
    }

    fun upDateItem(item: Any?) {
        when (item) {
            is UIModel.CategoryModel -> db.collection(CATEGORIES).document("${item.id}").update(
                mapOf(
                    UID to item.uid,
                    CATEGORY to item.category,
                    ICON to item.icon,
                    TRANSACTION_TYPE to item.type
                )
            )
            is UIModel.AccountModel -> db.collection(USERS).document("${item.id}").update(
                mapOf(
                    UID to item.uid,
                    PARTNER_UID to item.partnerUid
                )
            )
            is UIModel.TransactionModel -> db.collection(TRANSACTIONS).document("${item.id}").update(
                mapOf(
                    UID to item.uid,
                    TRANSACTION_TYPE to item.type,
                    CATEGORY to item.category,
                    CURRENCY to item.currency,
                    MONEY_TYPE to item.moneyType,
                    VALUE to item.value,
                    DATE to item.date
                )
            )
        }
    }
}