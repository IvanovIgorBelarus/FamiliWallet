package com.example.familiwallet.core.repository.domain

import com.example.familiwallet.core.data.UIModel

object SmsCache : CacheRepository<List<UIModel.SmsModel>> {

    private var smsList: List<UIModel.SmsModel>? = null

    override fun put(cache: List<UIModel.SmsModel>) {
        smsList = cache
    }

    override fun get(): List<UIModel.SmsModel> = smsList.orEmpty()

    override fun clear() {
        smsList = null
    }

    override fun isEmpty(): Boolean = smsList.isNullOrEmpty()
}