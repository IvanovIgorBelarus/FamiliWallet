package com.example.expenseobserver.core.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import com.example.expenseobserver.core.common.CATEGORIES
import com.example.expenseobserver.core.common.CATEGORY
import com.example.expenseobserver.core.common.COLOR
import com.example.expenseobserver.core.common.CURRENCY
import com.example.expenseobserver.core.common.DATE
import com.example.expenseobserver.core.common.DESCRIPTION
import com.example.expenseobserver.core.common.ICON
import com.example.expenseobserver.core.common.MONEY_TYPE
import com.example.expenseobserver.core.common.NAME
import com.example.expenseobserver.core.common.PARTNER_UID
import com.example.expenseobserver.core.common.TRANSACTIONS
import com.example.expenseobserver.core.common.TRANSACTION_TYPE
import com.example.expenseobserver.core.common.UID
import com.example.expenseobserver.core.common.URL
import com.example.expenseobserver.core.common.USERS
import com.example.expenseobserver.core.common.VALUE
import com.example.expenseobserver.core.common.VERSION
import com.example.expenseobserver.core.common.WALLETS
import com.example.expenseobserver.core.data.CategoryColor
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UpdateOrAddRequestModel
import com.example.expenseobserver.core.utils.UserUtils
import com.google.firebase.firestore.QuerySnapshot
import kotlin.coroutines.resume

object RepositoryMapper {

    fun mapSmsList(response: QuerySnapshot): List<UIModel.SmsModel> {
        val smsList = mutableListOf<UIModel.SmsModel>()
        response.forEach { doc ->
            smsList.add(
                UIModel.SmsModel(
                    id = doc.id,
                    date = doc.getLong(DATE),
                    value = doc.getDouble(VALUE),
                    currency = doc.getString(CURRENCY),
                )
            )
        }
        return smsList
    }

    fun mapPartner(response: QuerySnapshot): UIModel.AccountModel {
        val partner = UIModel.AccountModel()
        response.forEach { doc ->
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
        return partner
    }

    fun mapPersonTransactionList(response: QuerySnapshot): List<UIModel.TransactionModel> {
        val transactionList = mutableListOf<UIModel.TransactionModel>()
        response.forEach { doc ->
            transactionList.add(
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
        return transactionList
    }

    fun getPersonCategoriesList(response: QuerySnapshot): List<UIModel.CategoryModel> {
        val list = mutableListOf<UIModel.CategoryModel>()
        response.forEach { doc ->
            list.add(
                UIModel.CategoryModel(
                    id = doc.id,
                    uid = doc.getString(UID),
                    category = doc.getString(CATEGORY),
                    type = doc.getString(TRANSACTION_TYPE),
                    icon = doc.getString(ICON) ?: Icons.Default.List.name,
                    color = doc.getString(COLOR) ?: CategoryColor.UNKNOWN.name
                )
            )
        }
        return list
    }

    fun getUpdateModel(response: QuerySnapshot): UIModel.UpdateModel = UIModel.UpdateModel().apply {
        val doc = response.firstOrNull()
        url = doc?.getString(URL)
        versionCode = doc?.getLong(VERSION)
        description = doc?.getString(DESCRIPTION)
    }

    fun getPersonWalletsList(response: QuerySnapshot): List<UIModel.WalletModel> {
        val list = mutableListOf<UIModel.WalletModel>()
        response.forEach { doc ->
            list.add(
                UIModel.WalletModel(
                    id = doc.id,
                    uid = doc.getString(UID),
                    name = doc.getString(NAME),
                    currency = doc.getString(CURRENCY),
                    value = doc.getDouble(VALUE)
                )
            )
        }
        return list
    }

    fun mapUpdateOrAddRequestInfo(item: UIModel?): DataResponse<UpdateOrAddRequestModel> {
        var requestModel = UpdateOrAddRequestModel()
        when (item) {
            is UIModel.CategoryModel -> {
                requestModel.collectionPath = CATEGORIES
                requestModel.itemId = item.id.orEmpty()
                requestModel.data = mapOf(
                    UID to item.uid,
                    CATEGORY to item.category,
                    ICON to item.icon,
                    TRANSACTION_TYPE to item.type,
                    COLOR to item.color
                )
            }
            is UIModel.AccountModel -> {
                requestModel.collectionPath = USERS
                requestModel.itemId = item.id.orEmpty()
                requestModel.data = mapOf(
                    UID to item.uid,
                    PARTNER_UID to item.partnerUid
                )
            }
            is UIModel.TransactionModel -> {
                requestModel.collectionPath = TRANSACTIONS
                requestModel.itemId = item.id.orEmpty()
                requestModel.data = mapOf(
                    UID to item.uid,
                    TRANSACTION_TYPE to item.type,
                    CATEGORY to item.category,
                    CURRENCY to item.currency,
                    MONEY_TYPE to item.moneyType,
                    VALUE to item.value,
                    DATE to item.date
                )
            }
            is UIModel.WalletModel -> {
                requestModel.collectionPath = WALLETS
                requestModel.itemId = item.id.orEmpty()
                requestModel.data = mapOf(
                    UID to item.uid,
                    NAME to item.name,
                    CURRENCY to item.currency,
                    VALUE to item.value
                )
            }
            else -> return DataResponse.Error(Throwable("не удалось обновить запись"))
        }
        return DataResponse.Success(requestModel)
    }
}