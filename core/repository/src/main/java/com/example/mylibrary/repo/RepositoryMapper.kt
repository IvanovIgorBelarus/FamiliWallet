package com.example.mylibrary.repo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import com.example.data.CategoryColor
import com.example.data.DataResponse
import com.example.data.UIModel
import com.example.data.UpdateOrAddRequestModel
import com.google.firebase.firestore.QuerySnapshot

object RepositoryMapper {

    fun mapItems(response: QuerySnapshot, collectionName: String): List<UIModel> {
        return when (collectionName) {
            com.example.common.TRANSACTIONS -> mapPersonTransactionList(response)
            com.example.common.CATEGORIES -> getPersonCategoriesList(response)
            com.example.common.WALLETS -> getPersonWalletsList(response)
            com.example.common.TRANSFERS -> getPersonTransferList(response)
            else -> emptyList()
        }
    }

    fun mapPartner(response: QuerySnapshot): UIModel.AccountModel {
        val partner = UIModel.AccountModel()
        response.forEach { doc ->
            if (doc.getString(com.example.common.UID) == com.example.common.utils.UserUtils.getUsersUid() && doc.getString(com.example.common.PARTNER_UID) != null) {
                partner.id = doc.id
                partner.uid = com.example.common.utils.UserUtils.getUsersUid()
                partner.partnerUid = doc.getString(com.example.common.PARTNER_UID)
                return@forEach
            } else if (doc.getString(com.example.common.UID) == com.example.common.utils.UserUtils.getUsersUid()) {
                partner.id = doc.id
                partner.uid = com.example.common.utils.UserUtils.getUsersUid()
                partner.partnerUid = doc.getString(com.example.common.PARTNER_UID)
            }
        }
        return partner
    }

    private fun mapPersonTransactionList(response: QuerySnapshot): List<UIModel.TransactionModel> {
        val transactionList = mutableListOf<UIModel.TransactionModel>()
        response.forEach { doc ->
            transactionList.add(
                UIModel.TransactionModel(
                    id = doc.id,
                    uid = doc.getString(com.example.common.UID),
                    type = doc.getString(com.example.common.TRANSACTION_TYPE),
                    category = doc.getString(com.example.common.CATEGORY),
                    currency = doc.getString(com.example.common.CURRENCY),
                    moneyType = doc.getString(com.example.common.MONEY_TYPE),
                    value = doc.getDouble(com.example.common.VALUE),
                    date = doc.getLong(com.example.common.DATE)
                )
            )
        }
        return transactionList
    }

    private fun getPersonCategoriesList(response: QuerySnapshot): List<UIModel.CategoryModel> {
        val list = mutableListOf<UIModel.CategoryModel>()
        response.forEach { doc ->
            list.add(
                UIModel.CategoryModel(
                    id = doc.id,
                    uid = doc.getString(com.example.common.UID),
                    category = doc.getString(com.example.common.CATEGORY),
                    type = doc.getString(com.example.common.TRANSACTION_TYPE),
                    icon = doc.getString(com.example.common.ICON) ?: Icons.Default.List.name,
                    color = doc.getString(com.example.common.COLOR) ?: CategoryColor.UNKNOWN.name
                )
            )
        }
        return list
    }

    fun getUpdateModel(response: QuerySnapshot): UIModel.UpdateModel = UIModel.UpdateModel().apply {
        val doc = response.firstOrNull()
        url = doc?.getString(com.example.common.URL)
        versionCode = doc?.getLong(com.example.common.VERSION)
        description = doc?.getString(com.example.common.DESCRIPTION)
    }

    private fun getPersonWalletsList(response: QuerySnapshot): List<UIModel.WalletModel> {
        val list = mutableListOf<UIModel.WalletModel>()
        response.forEach { doc ->
            list.add(
                UIModel.WalletModel(
                    id = doc.id,
                    uid = doc.getString(com.example.common.UID),
                    name = doc.getString(com.example.common.NAME),
                    currency = doc.getString(com.example.common.CURRENCY),
                    value = doc.getDouble(com.example.common.VALUE),
                    backgroundColor = doc.getString(com.example.common.BACKGROUND_COLOR),
                    nameBackgroundColor = doc.getString(com.example.common.NAME_BACKGROUND_COLOR),
                    isMainSource = doc.getBoolean(com.example.common.IS_MAIN_SOURCE) ?: false,
                )
            )
        }
        return list
    }

    private fun getPersonTransferList(response: QuerySnapshot): List<UIModel.TransferModel> {
        val list = mutableListOf<UIModel.TransferModel>()
        response.forEach { doc ->
            list.add(
                UIModel.TransferModel(
                    id = doc.id,
                    uid = doc.getString(com.example.common.UID),
                    sourceId = doc.getString(com.example.common.SOURCE_ID),
                    targetId = doc.getString(com.example.common.TARGET_ID),
                    date = doc.getLong(com.example.common.DATE),
                    value = doc.getDouble(com.example.common.VALUE)
                )
            )
        }
        return list
    }

    fun mapUpdateOrAddRequestInfo(item: UIModel?): DataResponse<UpdateOrAddRequestModel> {
        var requestModel = UpdateOrAddRequestModel()
        when (item) {
            is UIModel.CategoryModel -> {
                requestModel.collectionPath = com.example.common.CATEGORIES
                requestModel.itemId = item.id.orEmpty()
                requestModel.data = mapOf(
                    com.example.common.UID to item.uid,
                    com.example.common.CATEGORY to item.category,
                    com.example.common.ICON to item.icon,
                    com.example.common.TRANSACTION_TYPE to item.type,
                    com.example.common.COLOR to item.color
                )
            }
            is UIModel.AccountModel -> {
                requestModel.collectionPath = com.example.common.USERS
                requestModel.itemId = item.id.orEmpty()
                requestModel.data = mapOf(
                    com.example.common.UID to item.uid,
                    com.example.common.PARTNER_UID to item.partnerUid
                )
            }
            is UIModel.TransactionModel -> {
                requestModel.collectionPath = com.example.common.TRANSACTIONS
                requestModel.itemId = item.id.orEmpty()
                requestModel.data = mapOf(
                    com.example.common.UID to item.uid,
                    com.example.common.TRANSACTION_TYPE to item.type,
                    com.example.common.CATEGORY to item.category,
                    com.example.common.CURRENCY to item.currency,
                    com.example.common.MONEY_TYPE to item.moneyType,
                    com.example.common.VALUE to item.value,
                    com.example.common.DATE to item.date
                )
            }
            is UIModel.WalletModel -> {
                requestModel.collectionPath = com.example.common.WALLETS
                requestModel.itemId = item.id.orEmpty()
                requestModel.data = mapOf(
                    com.example.common.UID to item.uid,
                    com.example.common.NAME to item.name,
                    com.example.common.CURRENCY to item.currency,
                    com.example.common.VALUE to item.value,
                    com.example.common.BACKGROUND_COLOR to item.backgroundColor,
                    com.example.common.NAME_BACKGROUND_COLOR to item.nameBackgroundColor,
                    com.example.common.IS_MAIN_SOURCE to item.isMainSource
                )
            }
            is UIModel.TransferModel -> {
                requestModel.collectionPath = com.example.common.TRANSFERS
                requestModel.itemId = item.id.orEmpty()
                requestModel.data = mapOf(
                    com.example.common.UID to item.uid,
                    com.example.common.SOURCE_ID to item.sourceId,
                    com.example.common.TARGET_ID to item.targetId,
                    com.example.common.DATE to item.date,
                    com.example.common.VALUE to item.value
                )
            }
            else -> return DataResponse.Error(Throwable("не удалось обновить запись"))
        }
        return DataResponse.Success(requestModel)
    }
}