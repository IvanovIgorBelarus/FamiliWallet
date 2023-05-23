package com.example.expenseobserver.features.settingsscreen

import com.example.mylibrary.base.BaseUseCase
import com.example.mylibrary.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : BaseViewModel<Unit, BaseUseCase>(){
    override fun getData(forceLoad: Boolean) {
        TODO("Not yet implemented")
    }
}