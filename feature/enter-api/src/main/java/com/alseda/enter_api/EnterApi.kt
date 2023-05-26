package com.alseda.enter_api

import com.example.navigation.FeatureApi

interface EnterApi : FeatureApi {
    val enterScreenRoute: String

    val mainScreenRoute: String

    val authScreenRoute: String
}