package com.example.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common.rippleClickable
import com.example.data.theme.backgroundColor
import com.example.data.theme.buttonColor
import com.example.data.theme.textColor

@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    text: Int = com.example.data.R.string.enter,
    isSelected: MutableState<Boolean>,
    onClick: () -> Unit = {}
) {
    val resources = LocalContext.current.resources

    Box(
        modifier = modifier
            .padding(4.dp)
            .rippleClickable(backgroundColor) { onClick.invoke() }
            .requiredHeight(44.dp)
            .border(BorderStroke(if (isSelected.value) 0.dp else 1.dp, buttonColor), RoundedCornerShape(10.dp))
            .background(if (isSelected.value) buttonColor else backgroundColor, RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = resources.getString(text),
            fontSize = 14.sp,
            color = if (isSelected.value) Color.White else textColor
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
private fun TransactionButtonPreview() {
    MainButton(isSelected = mutableStateOf(false))
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ButtonsLay(
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.Center) {
        MainButton(
            modifier = Modifier.weight(1f),
            text = com.example.data.R.string.cancel,
            isSelected = mutableStateOf(false)
        ) {
            onCancelClick.invoke()
        }
        Spacer(modifier = Modifier.size(24.dp))
        MainButton(
            modifier = Modifier.weight(1f),
            text = com.example.data.R.string.done,
            isSelected = mutableStateOf(true)
        ) {
            onConfirmClick.invoke()
        }
        Spacer(modifier = Modifier.size(12.dp))
    }
}