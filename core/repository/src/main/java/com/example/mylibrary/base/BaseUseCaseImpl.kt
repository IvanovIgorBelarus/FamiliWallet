package com.example.mylibrary.base

import com.example.data.DataResponse
import com.example.data.UIModel
import com.example.mylibrary.repo.DataRepository
import javax.inject.Inject

open class BaseUseCaseImpl @Inject constructor() : BaseUseCase {

    @Inject
    lateinit var repo: DataRepository

    override suspend fun addItem(item: UIModel): DataResponse<Unit> = repo.addItem(item)

    override suspend fun deleteItem(item: UIModel): DataResponse<Unit> = repo.deleteItem(item)

    override suspend fun updateItem(item: UIModel): DataResponse<Unit> = repo.updateItem(item)

    override suspend fun getItems(collectionName: String, forceLoad: Boolean): DataResponse<List<UIModel>>? =
        repo.getItems(collectionName, forceLoad)

}