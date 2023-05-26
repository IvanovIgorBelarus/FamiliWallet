package com.alseda.auth_api

import com.example.navigation.FeatureApi

interface AuthApi: FeatureApi {
    val authScreenRoute: String

    val mainScreenRoute: String
}