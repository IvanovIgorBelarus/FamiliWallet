package com.example.familiwallet.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.familiwallet.ui.theme.backgroundColor
import com.example.familiwallet.ui.theme.buttonColor
import com.example.familiwallet.ui.theme.textColor

@Composable
fun AmountTextField(
    amount: MutableState<String>,
    showError: MutableState<Boolean>
) {
    TextField(
        value = amount.value,
        onValueChange = {
            amount.value = it
            showError.value = false
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, buttonColor), RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = TextStyle(fontSize = 14.sp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = textColor,
            backgroundColor = backgroundColor,
            focusedIndicatorColor = backgroundColor,
            unfocusedIndicatorColor = backgroundColor
        )
    )
}