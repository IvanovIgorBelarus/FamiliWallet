package com.alseda.auth_impl.presentation

import androidx.lifecycle.ViewModel
import com.example.expenseobserver.features.main.domain.usecase.PartnerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val partnerUseCase: PartnerUseCase
) : ViewModel() {

}