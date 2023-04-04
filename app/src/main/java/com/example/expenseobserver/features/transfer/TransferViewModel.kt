package com.example.expenseobserver.features.transfer

import com.example.expenseobserver.core.BaseViewModel
import com.example.expenseobserver.features.transfer.data.TransferScreenViewState
import com.example.expenseobserver.features.transfer.domain.TransferUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(): BaseViewModel<TransferScreenViewState, TransferUseCase>(){

    override fun getData(forceLoad: Boolean) {
        TODO("Not yet implemented")
    }

}