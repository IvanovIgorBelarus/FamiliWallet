package com.example.expenseobserver.core.di

import com.alseda.auth_api.AuthApi
import com.alseda.enter_api.EnterApi
import com.alseda.splashscreen_api.SplashScreenApi
import javax.inject.Inject

class DependencyFeatureProvider @Inject constructor(){
    @Inject
    lateinit var splashScreenApi: SplashScreenApi
    @Inject
    lateinit var authApi: AuthApi
    @Inject
    lateinit var enterApi: EnterApi

    fun splashScreen(): SplashScreenApi = splashScreenApi
    fun auth(): AuthApi = authApi
    fun enter(): EnterApi = enterApi
}