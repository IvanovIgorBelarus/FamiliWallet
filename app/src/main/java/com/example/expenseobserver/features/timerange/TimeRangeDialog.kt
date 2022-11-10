package com.example.expenseobserver.features.timerange

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
import androidx.compose.ui.ExperimentalComposeUiApi
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
import com.example.expenseobserver.App.Companion.dateFilterType
import com.example.expenseobserver.R
import com.example.expenseobserver.components.AmountTextField
import com.example.expenseobserver.components.MainButton
import com.example.expenseobserver.components.SwitchWithText
import com.example.expenseobserver.core.common.TimeRangeType
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.utils.toCountryDateFormat
import com.example.expenseobserver.core.utils.toStringDayFormat
import com.example.expenseobserver.ui.theme.backgroundColor
import com.example.expenseobserver.ui.theme.buttonColor
import com.example.expenseobserver.ui.theme.mainColor
import com.example.expenseobserver.ui.theme.textColor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TimeRangeDialog(
    modifier: Modifier = Modifier,
    dismissDialog: () -> Unit,
    onButtonClick: () -> Unit
) {
    val resources = LocalContext.current.resources
    val dialogWidth = LocalConfiguration.current.screenWidthDp.dp * 9 / 10
    val timeRange = remember { mutableStateOf(TimeRangeType.UNKNOWN) }
    var enableEditText = timeRange.value == TimeRangeType.RANGE

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
                    isChecked = timeRange.value == TimeRangeType.DAY,
                    onCheckedChange = { if (it) timeRange.value = TimeRangeType.DAY }
                )
                Spacer(modifier = Modifier.size(20.dp))

                SwitchWithText(
                    textResId = R.string.time_range_week_title,
                    isChecked = timeRange.value == TimeRangeType.WEEK,
                    onCheckedChange = { if (it) timeRange.value = TimeRangeType.WEEK }
                )
                Spacer(modifier = Modifier.size(20.dp))

                SwitchWithText(
                    textResId = R.string.time_range_month_title,
                    isChecked = timeRange.value == TimeRangeType.MONTH,
                    onCheckedChange = { if (it) timeRange.value = TimeRangeType.MONTH }
                )
                Spacer(modifier = Modifier.size(20.dp))

                SwitchWithText(
                    textResId = R.string.time_range_other_title,
                    isChecked = timeRange.value == TimeRangeType.RANGE,
                    onCheckedChange = { if (it) timeRange.value = TimeRangeType.RANGE }
                )
                Spacer(modifier = Modifier.size(20.dp))

                Text(
                    text = resources.getString(R.string.time_range_start),
                    color = textColor,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.size(8.dp))
                AmountTextField(
                    stringValue = mutableStateOf(dateFilterType.startDate.toCountryDateFormat),
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
                Spacer(modifier = Modifier.size(20.dp))

                Text(
                    text = resources.getString(R.string.time_range_end),
                    color = textColor,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.size(8.dp))
                AmountTextField(
                    stringValue = mutableStateOf(dateFilterType.endDate.toCountryDateFormat),
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