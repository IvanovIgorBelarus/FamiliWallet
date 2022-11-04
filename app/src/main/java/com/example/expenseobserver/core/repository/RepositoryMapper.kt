package com.example.expenseobserver.core.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import com.example.expenseobserver.core.common.CATEGORY
import com.example.expenseobserver.core.common.COLOR
import com.example.expenseobserver.core.common.CURRENCY
import com.example.expenseobserver.core.common.DATE
import com.example.expenseobserver.core.common.ICON
import com.example.expenseobserver.core.common.MONEY_TYPE
import com.example.expenseobserver.core.common.PARTNER_UID
import com.example.expenseobserver.core.common.TRANSACTION_TYPE
import com.example.expenseobserver.core.common.UID
import com.example.expenseobserver.core.common.URL
import com.example.expenseobserver.core.common.VALUE
import com.example.expenseobserver.core.common.VERSION
import com.example.expenseobserver.core.data.CategoryColor
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.utils.UserUtils
import com.google.firebase.firestore.QuerySnapshot

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
    }
}