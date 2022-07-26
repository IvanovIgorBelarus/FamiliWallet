package com.example.familiwallet.core.repository.domain

import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel

object SmsCache : CacheRepository<DataResponse<List<UIModel.SmsModel>>> {

    private var smsList: DataResponse<List<UIModel.SmsModel>>? = null

    override fun put(cache: DataResponse<List<UIModel.SmsModel>>) {
            smsList = cache
    }

    override fun get(): DataResponse<List<UIModel.SmsModel>>? = smsList

    override fun clear() {
        smsList = null
    }

    override fun isEmpty(): Boolean = smsList == null
}