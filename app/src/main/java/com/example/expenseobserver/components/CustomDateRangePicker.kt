package com.example.expenseobserver.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.util.Pair
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener

@Composable
fun CustomDateRangePicker(
    title: Int,
    onDateSelected: (Pair<Long, Long>) -> Unit = {}
): MaterialDatePicker<Pair<Long, Long>> {
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
        val listener = MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>> {
            if (it != null) onDateSelected(it)
            Log.e("MYNAME", "MaterialPickerOnPositiveButtonClickListener")
        }
        datePicker.addOnPositiveButtonClickListener(listener)
        onDispose {
            datePicker.removeOnPositiveButtonClickListener(listener)
        }
    }
    return datePicker
}

@Composable
fun CustomDatePickerDialog(
    title: String? = null,
    selectedDate: Long,
    onDateSelected: (Long) -> Unit = {}
): MaterialDatePicker<Long> {
    val constraints =
        CalendarConstraints.Builder()
            .setValidator(DateValidatorPointBackward.now())
            .build()

    val datePiker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("")
        .setSelection(selectedDate)
        .setCalendarConstraints(constraints)
        .build()

    DisposableEffect(datePiker) {
        val listener = MaterialPickerOnPositiveButtonClickListener<Long> {
            it?.let { onDateSelected(it) }
        }
        datePiker.addOnPositiveButtonClickListener(listener)
        onDispose {
            datePiker.removeOnPositiveButtonClickListener(listener)
        }
    }

    return datePiker
}

@Composable
fun rememberFragmentManager(): FragmentManager {
    val context = LocalContext.current
    return remember(context) {
        (context as FragmentActivity).supportFragmentManager
    }
}


