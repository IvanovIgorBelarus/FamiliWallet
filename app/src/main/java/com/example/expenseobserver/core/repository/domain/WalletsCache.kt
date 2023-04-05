//package com.example.expenseobserver.core.repository.domain
//
//import com.example.expenseobserver.core.data.DataResponse
//import com.example.expenseobserver.core.data.UIModel
//
//object WalletsCache: CacheRepository<DataResponse<List<UIModel.WalletModel>>> {
//
//    private val walletsList = mutableSetOf<UIModel.WalletModel>()
//
//    override suspend fun put(cache: DataResponse<List<UIModel.WalletModel>>) {
//        val list = (cache as DataResponse.Success).data
//        walletsList.addAll(list)
//    }
//
//    override suspend fun get(): DataResponse<List<UIModel.WalletModel>>? {
//        return DataResponse.Success(walletsList.toList())
//    }
//
//    override suspend fun clear() {
//        walletsList.clear()
//    }
//
//    override suspend fun isEmpty(): Boolean = walletsList.isEmpty()
//}