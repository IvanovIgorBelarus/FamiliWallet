package com.example.familiwallet.features.start_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.familiwallet.core.common.TimeRangeType
import com.example.familiwallet.core.common.currentDateFilter
import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.ui.UiState
import com.example.familiwallet.core.utils.UserUtils
import com.example.familiwallet.features.main.domain.usecase.PartnerUseCase
import com.example.familiwallet.features.start_screen.domain.usecase.StartScreenInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val startScreenInfoUseCase: StartScreenInfoUseCase,
    private val partnerUseCase: PartnerUseCase
) : ViewModel() {

    private val uiRangeState = mutableStateOf<UiState<TimeRangeType>>(UiState.Success(TimeRangeType.WEEK))
    private val uiState = mutableStateOf<UiState<StartScreenViewState>>(UiState.Loading)

    fun getUiState(): State<UiState<StartScreenViewState>> = uiState

    fun getUiRangeState(): State<UiState<TimeRangeType>> = uiRangeState

    fun setUiRangeState(timeRangeType: TimeRangeType) {
        viewModelScope.launch {
            uiRangeState.value = UiState.Success(timeRangeType)
        }
    }

    fun getMainScreenInfo(timeRangeType: TimeRangeType, forceLoad: Boolean = false) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                val categoriesList = mutableListOf<UIModel.CategoryModel>()
                val transactionsList = mutableListOf<UIModel.TransactionModel>()
                val userData = getPersonData(UserUtils.getUsersUid().orEmpty(), forceLoad)
                categoriesList.addAll(userData.first)
                transactionsList.addAll(userData.second)

//                val partner = partnerUseCase.getPartner(forceLoad)
//                if (partner is DataResponse.Success) {
//                    val partnerData = getPersonData(partner.data.partnerUid.orEmpty(), forceLoad)
//                    categoriesList.addAll(partnerData.first)
//                    transactionsList.addAll(partnerData.second)
//                }
                uiState.value = UiState.Success(
                    StartScreenViewState(
                        categoriesList = categoriesList,
                        transactionsList = transactionsList.sortedByDescending { it.date }
                    )
                )
            } catch (e: Exception) {
                uiState.value = UiState.Error(e)
            }
        }
    }

    private suspend fun getPersonData(uid: String, forceLoad: Boolean) = viewModelScope.async {
        try {
            val categoryListResponse = startScreenInfoUseCase.getCategoriesList(uid, forceLoad)
            val categoriesList = mutableListOf<UIModel.CategoryModel>()
            when (categoryListResponse) {
                is DataResponse.Success -> {
                    categoriesList.addAll(categoryListResponse.data)
                }
                is DataResponse.Error -> {
                    uiState.value = UiState.Error(categoryListResponse.exception)
                    Log.w("ERROR", "categoryListResponse failed", categoryListResponse.exception)
                }
            }

            val transactionsListResponse = startScreenInfoUseCase.getTransactionsList(uid, forceLoad)
            val transactionsList = mutableListOf<UIModel.TransactionModel>()
            when (transactionsListResponse) {
                is DataResponse.Success -> {
                    transactionsList.addAll(transactionsListResponse.data.currentDateFilter())
                }
                is DataResponse.Error -> {
                    uiState.value = UiState.Error(transactionsListResponse.exception)
                    Log.w("ERROR", "transactionsListResponse failed", transactionsListResponse.exception)
                }
                else -> {}
            }
            return@async Pair(categoriesList, transactionsList)
        } catch (e: Exception) {
            uiState.value = UiState.Error(e)
            return@async Pair(emptyList<UIModel.CategoryModel>(), emptyList<UIModel.TransactionModel>())
        }
    }.await()

}