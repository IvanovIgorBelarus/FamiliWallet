package com.example.familiwallet.features.authScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.familiwallet.core.data.DataResponse
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.repository.DataRepository
import com.example.familiwallet.core.utils.UserUtils
import com.example.familiwallet.features.main.domain.usecase.PartnerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val partnerUseCase: PartnerUseCase
) : ViewModel() {

}