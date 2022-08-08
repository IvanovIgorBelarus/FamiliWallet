package com.example.familiwallet.core.repository

import android.util.Log
import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.repository.domain.CacheRepository
import com.example.familiwallet.core.repository.domain.CategoriesCache
import com.example.familiwallet.core.repository.domain.PartnerCache
import com.example.familiwallet.core.repository.domain.SmsCache
import com.example.familiwallet.core.repository.domain.TransactionsCache
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataInteractor @Inject constructor(
    private val firebaseRepository: FirebaseRepositoryImpl
) : DataRepository {

    override suspend fun addPartner(accountModel: UIModel.AccountModel): DataResponse<Unit> =
        firebaseRepository.addPartner(accountModel)

    override suspend fun doTransaction(transactionModel: UIModel.TransactionModel): DataResponse<Unit> =
        firebaseRepository.doTransaction(transactionModel)

    override suspend fun doBakTransactions(transactionModel: UIModel.TransactionModel): DataResponse<Unit> =
        firebaseRepository.doBakTransactions(transactionModel)

    override suspend fun addNewCategory(categoryItem: UIModel.CategoryModel) {
        firebaseRepository.addNewCategory(categoryItem)
    }

    override suspend fun getSmsList(forceLoad: Boolean): DataResponse<List<UIModel.SmsModel>>? {
        return get(SmsCache, firebaseRepository.getSmsList(), forceLoad)
    }


    override suspend fun getPartner(forceLoad: Boolean): DataResponse<UIModel.AccountModel>? {
        return get(PartnerCache, firebaseRepository.getPartner(), forceLoad)
    }

    override suspend fun getTransactionsList(forceLoad: Boolean): DataResponse<List<UIModel.TransactionModel>>? {
        return get(TransactionsCache, firebaseRepository.getTransactionsList(getPartner()), forceLoad)
    }

    override suspend fun getCategoriesList(forceLoad: Boolean): DataResponse<List<UIModel.CategoryModel>>? {
        return get(CategoriesCache, firebaseRepository.getCategoriesList(getPartner()), forceLoad)
    }

    override suspend fun deleteItem(item: Any?) {
        firebaseRepository.deleteItem(item)
    }

    override suspend fun upDateItem(item: Any?) {
        firebaseRepository.upDateItem(item)
        update(item)
    }

    private fun <T> get(cache: CacheRepository<T>, request: T, forceLoad: Boolean): T? {
        return if (cache.isEmpty() || forceLoad) {
            Log.i("MYNAME","request")
            cache.put(request)
            cache.get()
        } else {
            Log.i("MYNAME","Cash")
            cache.get()
        }
    }


    suspend fun update(item: Any?) {
        when (item) {
            is UIModel.CategoryModel -> getCategoriesList(forceLoad = true)
            is UIModel.AccountModel -> getPartner(forceLoad = true)
            is UIModel.TransactionModel -> getTransactionsList(forceLoad = true)
            is UIModel.SmsModel -> getSmsList(forceLoad = true)
        }
//        EventBus.getDefault().post(UpdateWrapper())
    }
}