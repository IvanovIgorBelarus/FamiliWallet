package com.example.expenseobserver.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expenseobserver.core.common.disableColor
import com.example.expenseobserver.ui.theme.backgroundColor
import com.example.expenseobserver.ui.theme.bottomBarUnselectedContentColor
import com.example.expenseobserver.ui.theme.textColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmountTextField(
    stringValue: MutableState<String>,
    placeHolderText: String? = null,
    modifier: Modifier = Modifier,
    showError: MutableState<Boolean>,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    inputTextColor: Color = textColor,
    textBackgroundColor: Color = backgroundColor,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    CustomInputTextField(
        value = stringValue.value,
        onValueChange = {
            stringValue.value = it
            showError.value = false
        },
        modifier = modifier
            .fillMaxWidth()
            .height(36.dp),
        placeholder = { AmountFieldPlaceHolder(placeHolderText = placeHolderText) },
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        textStyle = TextStyle(fontSize = 14.sp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = inputTextColor,
            disabledTextColor = inputTextColor.disableColor(),
            containerColor = textBackgroundColor,
            focusedIndicatorColor = textBackgroundColor,
            unfocusedIndicatorColor = textBackgroundColor
        ),
        enabled = enabled,
        readOnly = readOnly,
        trailingIcon = trailingIcon
    )
}

@Composable
private fun AmountFieldPlaceHolder(placeHolderText: String?) {
    if (!placeHolderText.isNullOrEmpty()) {
        Text(
            text = placeHolderText,
            color = bottomBarUnselectedContentColor,
            textAlign = TextAlign.Start,
            fontSize = 14.sp
        )
    }
}