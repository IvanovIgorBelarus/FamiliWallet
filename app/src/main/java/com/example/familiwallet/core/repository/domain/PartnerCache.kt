package com.example.familiwallet.core.repository.domain

import android.util.Log
import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel

object PartnerCache : CacheRepository<DataResponse<UIModel.AccountModel>> {

    private var partner: DataResponse<UIModel.AccountModel>? = null

    override suspend fun put(cache: DataResponse<UIModel.AccountModel>) {
        partner = cache
    }

    override suspend fun get(): DataResponse<UIModel.AccountModel>? {
        Log.d("MYNAME", "PartnerCache")
        return partner
    }

    override suspend fun clear() {
        partner = null
    }

    override suspend fun isEmpty(): Boolean = partner == null
}