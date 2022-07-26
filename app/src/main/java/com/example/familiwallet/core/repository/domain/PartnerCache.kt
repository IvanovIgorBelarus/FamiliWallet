package com.example.familiwallet.core.repository.domain

import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel

object PartnerCache: CacheRepository<DataResponse<UIModel.AccountModel>> {

    private  var partner: DataResponse<UIModel.AccountModel>? = null

    override fun put(cache: DataResponse<UIModel.AccountModel>) {
        partner = cache
    }

    override fun get(): DataResponse<UIModel.AccountModel>? = partner

    override fun clear() {
       partner = null
    }

    override fun isEmpty(): Boolean = partner == null
}