package com.example.familiwallet.core.repository.domain

import com.example.familiwallet.core.data.UIModel

object PartnerCache: CacheRepository<UIModel.AccountModel> {

    private  var partner: UIModel.AccountModel? = null

    override fun put(cache: UIModel.AccountModel) {
        partner = cache
    }

    override fun get(): UIModel.AccountModel = partner!!

    override fun clear() {
       partner = null
    }

    override fun isEmpty(): Boolean = partner == null
}