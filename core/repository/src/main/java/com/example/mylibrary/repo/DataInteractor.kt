package com.example.mylibrary.repo

import android.util.Log
import com.example.data.DataResponse
import com.example.data.UIModel
import com.example.mylibrary.repo.domain.BaseCache
import com.example.mylibrary.repo.domain.CacheRepository
import com.example.mylibrary.repo.domain.PartnerCache
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataInteractor @Inject constructor(
    private val firebaseRepository: FirebaseRepositoryImpl
) : DataRepository {

    override suspend fun getPartner(forceLoad: Boolean): DataResponse<UIModel.AccountModel>? {
        return get(PartnerCache, firebaseRepository.getPartner(), forceLoad, "getPartner")
    }

    override suspend fun addItem(item: UIModel?): DataResponse<Unit> = firebaseRepository.addItem(item)


    override suspend fun deleteItem(item: UIModel?): DataResponse<Unit> =
        firebaseRepository.deleteItem(item)

    override suspend fun updateItem(item: UIModel?): DataResponse<Unit> =
        firebaseRepository.upDateItem(item)

    override suspend fun getItems(collectionName: String, forceLoad: Boolean): DataResponse<List<UIModel>>? =
        get(BaseCache, firebaseRepository.getItems(getPartner(), collectionName), forceLoad, collectionName)

    override suspend fun checkUpdates(): DataResponse<UIModel.UpdateModel> =
        firebaseRepository.checkUpdates()

    private suspend fun <T> get(cache: CacheRepository<T>, request: T, forceLoad: Boolean, requestName: String): T? {
        return if (cache.isEmpty(requestName) || forceLoad) {
            Log.e("MYNAME", "$requestName request")
            if (request is DataResponse.Success<*>) {
                cache.clear(requestName)
                cache.put(requestName, request)
                cache.get(requestName)
            } else {
                request
            }
        } else {
            Log.d("MYNAME", "$requestName cache")
            cache.get(requestName)
        }
    }
}