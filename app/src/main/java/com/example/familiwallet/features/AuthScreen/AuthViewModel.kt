package com.example.familiwallet.features.AuthScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.repository.DataRepository
import com.example.familiwallet.core.utils.UserUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: DataRepository
) : ViewModel() {
    fun addUserInCloud(doOnSuccess: () -> Unit) {
        viewModelScope.launch {
            val partner = repo.getPartner()
            val isPartner = partner is DataResponse.Success && partner.data.uid.isNullOrEmpty()
            if (isPartner) {
                val response = repo.addPartner(UIModel.AccountModel(uid = UserUtils.getUsersUid()))
                if (response is DataResponse.Success) {
                    doOnSuccess.invoke()
                }
            }
            doOnSuccess.invoke()
        }
    }
}