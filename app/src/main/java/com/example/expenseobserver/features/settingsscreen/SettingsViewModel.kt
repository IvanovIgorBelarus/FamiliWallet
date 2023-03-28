package com.example.expenseobserver.features.settingsscreen

import com.example.expenseobserver.core.BaseUseCase
import com.example.expenseobserver.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : BaseViewModel<Unit, BaseUseCase>(){
    override fun getData(forceLoad: Boolean) {
        TODO("Not yet implemented")
    }
}