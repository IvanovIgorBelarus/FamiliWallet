package com.example.expenseobserver.components

import android.content.DialogInterface
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
    dismiss: () -> Unit,
    onDateSelected: (Pair<Long, Long>) -> Unit = {}
): MaterialDatePicker<Pair<Long, Long>> {
    val constraints =
        CalendarConstraints.Builder()
            .setValidator(DateValidatorPointBackward.now())
            .build()

    val datePicker = MaterialDatePicker.Builder.dateRangePicker()
        .setTitleText("")
        .setSelection(
            Pair(
                MaterialDatePicker.thisMonthInUtcMilliseconds(),
                MaterialDatePicker.todayInUtcMilliseconds()
            )
        )
        .setCalendarConstraints(constraints)
        .build()

    DisposableEffect(datePicker) {
        val positiveListener = MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>> {
           onDateSelected.invoke(it)
        }
        val dismissListener = DialogInterface.OnDismissListener {
            dismiss.invoke()
        }
        with(datePicker) {
            addOnPositiveButtonClickListener(positiveListener)
            addOnDismissListener(dismissListener)
            onDispose {
                removeOnPositiveButtonClickListener(positiveListener)
                removeOnDismissListener(dismissListener)
            }
        }
    }
    return datePicker
}

@Composable
fun CustomDatePickerDialog(
    title: String? = null,
    selectedDate: Long,
    dismiss: () -> Unit,
    onDateSelected: (Long) -> Unit = {}
): MaterialDatePicker<Long> {
    val constraints =
        CalendarConstraints.Builder()
            .setValidator(DateValidatorPointBackward.now())
            .build()

    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("")
        .setSelection(selectedDate)
        .setCalendarConstraints(constraints)
        .build()

    DisposableEffect(datePicker) {
        val positiveListener = MaterialPickerOnPositiveButtonClickListener<Long> {
            onDateSelected.invoke(it)
        }
        val dismissListener = DialogInterface.OnDismissListener {
            dismiss.invoke()
        }
        with(datePicker) {
            addOnPositiveButtonClickListener(positiveListener)
            addOnDismissListener(dismissListener)
            onDispose {
                removeOnPositiveButtonClickListener(positiveListener)
                removeOnDismissListener(dismissListener)
            }
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


