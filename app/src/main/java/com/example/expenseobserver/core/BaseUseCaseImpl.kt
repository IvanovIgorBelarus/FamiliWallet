package com.example.expenseobserver.core

import com.example.expenseobserver.core.data.DataResponse
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.repository.DataRepository
import javax.inject.Inject

open class BaseUseCaseImpl @Inject constructor() : BaseUseCase {

    @Inject
    lateinit var repo: DataRepository

    override suspend fun addItem(item: UIModel): DataResponse<Unit> = repo.addItem(item)

    override suspend fun deleteItem(item: UIModel): DataResponse<Unit> = repo.deleteItem(item)

    override suspend fun updateItem(item: UIModel): DataResponse<Unit> = repo.updateItem(item)

}