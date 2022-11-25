package com.example.expenseobserver

import android.app.Application
import com.example.expenseobserver.core.common.TimeRangeType
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application(){
    companion object {
        var dateFilterType: TimeRangeType = TimeRangeType.MONTH
    }
}