package com.alseda.splashscreen_api

import com.example.navigation.FeatureApi

interface SplashScreenApi : FeatureApi {
    val splashScreenRoute: String

    val enterScreenRoute: String
}