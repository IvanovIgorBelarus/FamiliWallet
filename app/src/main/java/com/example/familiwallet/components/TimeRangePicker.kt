package com.example.familiwallet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.familiwallet.core.common.TimeRangeType
import com.example.familiwallet.ui.theme.expensesBackgroundColor
import com.example.familiwallet.ui.theme.expensesColor
import com.example.familiwallet.ui.theme.incomesBackgroundColor
import com.example.familiwallet.ui.theme.incomesColor

@Composable
fun TimeRangePicker(
    modifier: Modifier = Modifier,
    selectedTimeRange: TimeRangeType,
    onTimeRangeClicked: (TimeRangeType) -> Unit
) {
    Row(
        modifier = modifier.padding(8.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TimeRangeBox(time = TimeRangeType.DAY.text, isSelected = selectedTimeRange == TimeRangeType.DAY) {
            onTimeRangeClicked(TimeRangeType.DAY)
        }
        TimeRangeBox(time = TimeRangeType.WEEK.text, isSelected = selectedTimeRange == TimeRangeType.WEEK) {
            onTimeRangeClicked(TimeRangeType.WEEK)
        }
        TimeRangeBox(time = TimeRangeType.MONTH.text, isSelected = selectedTimeRange == TimeRangeType.MONTH) {
            onTimeRangeClicked(TimeRangeType.MONTH)
        }
    }
}

@Composable
fun TimeRangeBox(
    time: String,
    isSelected: Boolean,
    onTimeRangeClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = if (isSelected) incomesBackgroundColor else expensesBackgroundColor,
                shape = RoundedCornerShape(16.dp)
            ).padding(4.dp)
            .clickable { onTimeRangeClicked() }
    ) {
        Text(
            text = time,
            color = if (isSelected) incomesColor else expensesColor,
            modifier = Modifier.padding(4.dp),
        )
    }
}