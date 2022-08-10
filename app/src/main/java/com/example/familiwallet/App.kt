package com.example.familiwallet

import android.app.Application
import com.example.familiwallet.core.common.WEEK_FILTER
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application(){
    companion object {
        var dateFilterType: String = WEEK_FILTER
        var startDate: Long? = null
        var endDate: Long? = null
    }
}