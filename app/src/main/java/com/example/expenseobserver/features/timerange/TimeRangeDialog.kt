package com.example.expenseobserver.features.timerange

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.common.noRippleClickable
import com.example.common.utils.toCountryDateFormat
import com.example.common.utils.toEndOfDay
import com.example.common.utils.toStartOfDay
import com.example.components.AmountTextField
import com.example.components.CustomDateRangePicker
import com.example.components.MainButton
import com.example.components.SwitchWithText
import com.example.components.rememberFragmentManager
import com.example.data.theme.backgroundColor
import com.example.data.theme.buttonColor
import com.example.data.theme.mainColor
import com.example.data.theme.textColor
import com.example.expenseobserver.App.Companion.dateFilterType
import com.example.expenseobserver.R
import java.util.*

@SuppressLint("UnrememberedMutableState")
@Composable
fun TimeRangeDialog(
    modifier: Modifier = Modifier,
    dismissDialog: () -> Unit,
    onButtonClick: () -> Unit
) {
    val resources = LocalContext.current.resources
    val dialogWidth = LocalConfiguration.current.screenWidthDp.dp * 9 / 10
    val timeRange = remember { mutableStateOf(com.example.common.TimeRangeType.UNKNOWN) }
    val enableEditText = timeRange.value == com.example.common.TimeRangeType.RANGE

    val showDatePicker = remember { mutableStateOf(false) }

    val datePicker = CustomDateRangePicker(
        title = R.string.pets ,
        dismiss = {  showDatePicker.value = false },
        onDateSelected = {
            timeRange.value = com.example.common.TimeRangeType.RANGE.apply {
                startDate = Date(it.first).toStartOfDay.time
                endDate = Date(it.second).toEndOfDay.time
            }
            showDatePicker.value = false
        })

    if (showDatePicker.value) {
        datePicker.show(rememberFragmentManager(), "Date")
    }

    Dialog(
        onDismissRequest = { dismissDialog.invoke() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            contentAlignment = Alignment.Center, modifier = modifier
                .width(dialogWidth)
                .wrapContentHeight()
                .background(backgroundColor, RoundedCornerShape(10.dp))
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
            ) {
                Text(
                    text = LocalContext.current.resources.getString(R.string.time_range_title),
                    color = textColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.size(20.dp))

                SwitchWithText(
                    textResId = R.string.time_range_day_title,
                    isChecked = timeRange.value == com.example.common.TimeRangeType.DAY,
                    onCheckedChange = { if (it) timeRange.value = com.example.common.TimeRangeType.DAY }
                )
                Spacer(modifier = Modifier.size(20.dp))

                SwitchWithText(
                    textResId = R.string.time_range_week_title,
                    isChecked = timeRange.value == com.example.common.TimeRangeType.WEEK,
                    onCheckedChange = { if (it) timeRange.value = com.example.common.TimeRangeType.WEEK }
                )
                Spacer(modifier = Modifier.size(20.dp))

                SwitchWithText(
                    textResId = R.string.time_range_month_title,
                    isChecked = timeRange.value == com.example.common.TimeRangeType.MONTH,
                    onCheckedChange = { if (it) timeRange.value = com.example.common.TimeRangeType.MONTH }
                )
                Spacer(modifier = Modifier.size(20.dp))

                SwitchWithText(
                    textResId = R.string.time_range_other_title,
                    isChecked = timeRange.value == com.example.common.TimeRangeType.RANGE,
                    onCheckedChange = { if (it) timeRange.value = com.example.common.TimeRangeType.RANGE }
                )
                Spacer(modifier = Modifier.size(20.dp))

                Column(modifier = Modifier.noRippleClickable {
                    if (timeRange.value == com.example.common.TimeRangeType.RANGE) showDatePicker.value = true
                }) {
                    Text(
                        text = resources.getString(R.string.time_range_start),
                        color = textColor,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.size(8.dp))

                    AmountTextField(
                        stringValue = mutableStateOf(timeRange.value.startDate.toCountryDateFormat),
                        modifier = Modifier
                            .border(BorderStroke(1.dp, buttonColor), RoundedCornerShape(10.dp)),
                        showError = mutableStateOf(false),
                        enabled = enableEditText,
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_time_range),
                                contentDescription = "",
                                tint = mainColor.copy(alpha = if (enableEditText) 1f else 0.38f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.size(20.dp))

                    Text(
                        text = resources.getString(R.string.time_range_end),
                        color = textColor,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    AmountTextField(
                        stringValue = mutableStateOf(timeRange.value.endDate.toCountryDateFormat),
                        modifier = Modifier.border(BorderStroke(1.dp, buttonColor), RoundedCornerShape(10.dp)),
                        showError = mutableStateOf(false),
                        enabled = enableEditText,
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_time_range),
                                contentDescription = "",
                                tint = mainColor.copy(alpha = if (enableEditText) 1f else 0.38f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.size(48.dp))
                }

                MainButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = R.string.edit,
                    isSelected = mutableStateOf(true)
                ) {
                    dateFilterType = timeRange.value
                    onButtonClick.invoke()
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        timeRange.value = dateFilterType
    }
}

@Preview(showBackground = true)
@Composable
fun TimeRangeScreenPreview() {
    TimeRangeDialog(dismissDialog = {}, onButtonClick = {})
}