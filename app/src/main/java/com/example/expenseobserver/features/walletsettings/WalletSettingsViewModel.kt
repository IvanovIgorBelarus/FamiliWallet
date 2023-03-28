package com.example.expenseobserver.features.walletsettings

import com.example.expenseobserver.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletSettingsViewModel @Inject constructor() : BaseViewModel<WalletSettingsViewState>() {

    override fun getData(forceLoad: Boolean) {

    }
}