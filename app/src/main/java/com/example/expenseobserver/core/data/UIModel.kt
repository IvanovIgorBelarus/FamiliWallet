package com.example.expenseobserver.core.data

sealed class UIModel {
    data class AccountModel(
        var id: String? = null,
        var uid: String? = null,
        var partnerUid: String? = null
    ) : BaseModel(id)

    data class CategoryModel(
        var id: String? = null,
        var uid: String? = null,
        var category: String? = null,
        var type: String? = null,
        var icon: String? = null,
        var color: String? = null,
        var count: Int = 0
    ) : BaseModel(id)

    data class StatisticModel(
        var category: String?,
        var type: String?,
        val value: Double?,
        var icon: String? = null
    )

    data class TransactionModel(
        var id: String? = null,
        var uid: String? = null,
        var type: String? = null,
        var category: String? = null,
        var currency: String? = null,
        var moneyType: String? = null,
        var date: Long? = null,
        var value: Double? = null
    ) : BaseModel(id)

    data class ArchiveMonthModel(
        val monthAndYear: String,
        val startDate: Long,
        val endDate: Long
    )

    data class SmsModel(
        var id: String? = null,
        var date: Long? = null,
        var value: Double? = null,
        var currency: String? = null
    ) : BaseModel(id)

    data class UpdateModel(
        var url: String? = null,
        var versionCode: Long? = null,
        var description: String? = null
    ): BaseModel(url)

    data class WalletModel(
        var id: String? = null,
        var uid: String? = null,
        var name: String? = null,
        var currency: String? = null,
        var value: Double? = null,
        var backgroundColor: String? = null,
        var nameBackgroundColor: String? = null,
        var isMainSource: Boolean = false
    ): BaseModel(id)

    data class TransferModel(
        var id: String? = null,
        var uid: String? = null,
        var sourceId: String? = null,
        var targetId: String? = null,
        var date: Long? = null,
        var value: Double? = null
    ): BaseModel(id)

    open class BaseModel(
        var itemId: String? = null
    ): UIModel()
}
