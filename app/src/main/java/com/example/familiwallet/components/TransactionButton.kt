package com.example.familiwallet.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
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
import com.example.familiwallet.R
import com.example.familiwallet.core.common.rippleClickable
import com.example.familiwallet.ui.theme.backgroundColor
import com.example.familiwallet.ui.theme.buttonColor
import com.example.familiwallet.ui.theme.textColor

@Composable
fun TransactionButton(
    modifier: Modifier = Modifier,
    text: Int = R.string.enter,
    isSelected: MutableState<Boolean>,
    onClick: () -> Unit = {}
) {
    val resources = LocalContext.current.resources

    Box(
        modifier = modifier
            .padding(4.dp)
            .rippleClickable { onClick.invoke() }
            .requiredHeight(36.dp)
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

@Preview(showBackground = true)
@Composable
private fun TransactionButtonPreview() {
    TransactionButton(isSelected = mutableStateOf(false))
}