package com.example.familiwallet

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application(){
    companion object {
        var dateFilterType: String = ""
        var startDate: Long? = null
        var endDate: Long? = null
    }
}