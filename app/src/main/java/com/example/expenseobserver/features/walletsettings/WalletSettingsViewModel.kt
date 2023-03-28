package com.example.expenseobserver.features.walletsettings

import com.example.expenseobserver.core.BaseUseCase
import com.example.expenseobserver.core.BaseViewModel
import com.example.expenseobserver.features.walletsettings.data.WalletSettingsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletSettingsViewModel @Inject constructor() : BaseViewModel<WalletSettingsViewState, BaseUseCase>() {

    override fun getData(forceLoad: Boolean) {

    }
}