package com.example.expenseobserver.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.util.Pair
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.expenseobserver.R
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener

@Composable
fun CustomDatePicker(
    title: Int,
    onDateSelected: (Pair<Long,Long>) -> Unit = {}
): MaterialDatePicker<Pair<Long,Long>> {
    val datePicker = MaterialDatePicker.Builder.dateRangePicker()
        .setTitleText("")
        .setSelection(
            Pair(
                MaterialDatePicker.thisMonthInUtcMilliseconds(),
                MaterialDatePicker.todayInUtcMilliseconds()
            )
        )
        .build()

    DisposableEffect(datePicker) {
        val listener = MaterialPickerOnPositiveButtonClickListener<Pair<Long,Long>> {
            if (it != null) onDateSelected(it)
        }
        datePicker.addOnPositiveButtonClickListener(listener)
        onDispose {
            datePicker.removeOnPositiveButtonClickListener(listener)
        }
    }
    return datePicker
}

@Composable
fun rememberFragmentManager(): FragmentManager {
    val context = LocalContext.current
    return remember(context) {
        (context as FragmentActivity).supportFragmentManager
    }
}


