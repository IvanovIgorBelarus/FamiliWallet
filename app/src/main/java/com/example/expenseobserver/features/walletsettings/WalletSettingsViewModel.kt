package com.example.expenseobserver.features.walletsettings

import com.example.expenseobserver.core.BaseUseCase
import com.example.expenseobserver.core.BaseViewModel
import com.example.expenseobserver.core.data.UiState
import com.example.expenseobserver.features.newcategory.data.NewCategoryModel
import com.example.expenseobserver.features.newcategory.data.NewCategoryViewState
import com.example.expenseobserver.features.walletsettings.data.NewWalletModel
import com.example.expenseobserver.features.walletsettings.data.WalletSettingsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletSettingsViewModel @Inject constructor() : BaseViewModel<WalletSettingsViewState, BaseUseCase>() {

    override fun getData(forceLoad: Boolean) {
        uiState.value = UiState.Success(WalletSettingsViewState(NewWalletModel.getModel()))
    }
}