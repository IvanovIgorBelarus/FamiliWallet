package com.alseda.start_api

import com.example.navigation.FeatureApi

interface StartApi: FeatureApi {
    val startScreenRoute: String

    val walletScreenRoute: String

    val walletSettingsScreenRoute: String
}