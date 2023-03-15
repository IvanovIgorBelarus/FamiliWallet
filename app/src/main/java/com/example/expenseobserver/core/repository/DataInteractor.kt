package com.example.expenseobserver.core.repository

import android.util.Log
import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.repository.domain.CacheRepository
import com.example.expenseobserver.core.repository.domain.CategoriesCache
import com.example.expenseobserver.core.repository.domain.PartnerCache
import com.example.expenseobserver.core.repository.domain.SmsCache
import com.example.expenseobserver.core.repository.domain.TransactionsCache
import com.example.expenseobserver.core.repository.domain.WalletsCache
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataInteractor @Inject constructor(
    private val firebaseRepository: FirebaseRepositoryImpl
) : DataRepository {

//    override suspend fun addPartner(accountModel: UIModel.AccountModel): DataResponse<Unit> =
//        firebaseRepository.addPartner(accountModel)

//    override suspend fun doTransaction(transactionModel: UIModel.TransactionModel): DataResponse<Unit> =
//        firebaseRepository.doTransaction(transactionModel)

//    override suspend fun doBakTransactions(transactionModel: UIModel.TransactionModel): DataResponse<Unit> =
//        firebaseRepository.doBakTransactions(transactionModel)

//    override suspend fun addNewCategory(categoryItem: UIModel.CategoryModel): DataResponse<Unit> =
//        firebaseRepository.addNewCategory(categoryItem)


    override suspend fun getSmsList(forceLoad: Boolean): DataResponse<List<UIModel.SmsModel>>? {
        return get(SmsCache, firebaseRepository.getSmsList(), forceLoad, "getSmsList")
    }


    override suspend fun getPartner(forceLoad: Boolean): DataResponse<UIModel.AccountModel>? {
        return get(PartnerCache, firebaseRepository.getPartner(), forceLoad, "getPartner")
    }

    override suspend fun getTransactionsList(forceLoad: Boolean): DataResponse<List<UIModel.TransactionModel>>? {
        return get(TransactionsCache, firebaseRepository.getPersonTransactionList(getPartner()), forceLoad, "getTransactionsList")
    }

    override suspend fun getCategoriesList(forceLoad: Boolean): DataResponse<List<UIModel.CategoryModel>>? {
        return get(CategoriesCache, firebaseRepository.getPersonCategoriesList(getPartner()), forceLoad, "getCategoriesList")
    }

    override suspend fun addItem(item: UIModel?): DataResponse<Unit> = firebaseRepository.addItem(item)


    override suspend fun deleteItem(item: UIModel?): DataResponse<Unit> =
        firebaseRepository.deleteItem(item)

    override suspend fun updateItem(item: UIModel?): DataResponse<Unit> =
        firebaseRepository.upDateItem(item)

    override suspend fun checkUpdates(): DataResponse<UIModel.UpdateModel> =
        firebaseRepository.checkUpdates()

    override suspend fun getWalletsList(forceLoad: Boolean): DataResponse<List<UIModel.WalletModel>>? {
        return get(WalletsCache, firebaseRepository.getWalletsList(getPartner()), forceLoad, "getWalletsList")
    }

    private suspend fun <T> get(cache: CacheRepository<T>, request: T, forceLoad: Boolean, requestName: String): T? {
        return if (cache.isEmpty() || forceLoad) {
            Log.e("MYNAME", "$requestName request")
            if (request is DataResponse.Success<*>) {
                cache.clear()
                cache.put(request)
                cache.get()
            } else {
                request
            }
        } else {
            Log.d("MYNAME", "$requestName cash")
            cache.get()
        }
    }
}