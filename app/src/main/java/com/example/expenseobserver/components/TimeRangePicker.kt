package com.example.expenseobserver.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expenseobserver.core.common.TimeRangeType
import com.example.expenseobserver.core.common.noRippleClickable
import com.example.expenseobserver.ui.theme.expensesBackgroundColor
import com.example.expenseobserver.ui.theme.expensesColor
import com.example.expenseobserver.ui.theme.incomesBackgroundColor
import com.example.expenseobserver.ui.theme.incomesColor

@Composable
fun TimeRangePicker(
    modifier: Modifier = Modifier,
    selectedTimeRange: TimeRangeType,
    onTimeRangeClicked: (TimeRangeType) -> Unit
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
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
            )
            .padding(4.dp)
            .noRippleClickable { onTimeRangeClicked() }
    ) {
        Text(
            text = time,
            color = if (isSelected) incomesColor else expensesColor,
            modifier = Modifier.padding(4.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeRangePickerPreview() {
    TimeRangePicker(
        modifier = Modifier,
        selectedTimeRange = TimeRangeType.MONTH,
        onTimeRangeClicked = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun TimeRangeBoxPreview() {
    TimeRangeBox(
        time = TimeRangeType.DAY.text,
        isSelected = TimeRangeType.MONTH == TimeRangeType.DAY,
        onTimeRangeClicked = {}
    )
}