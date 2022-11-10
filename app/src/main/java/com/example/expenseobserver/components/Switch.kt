package com.example.expenseobserver.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expenseobserver.ui.theme.bottomBarUnselectedContentColor
import com.example.expenseobserver.ui.theme.mainColor
import com.example.expenseobserver.ui.theme.textColor
import com.example.expenseobserver.ui.theme.uncheckedThumbColor

@Composable
fun SwitchWithText(
    text: String? = null,
    textResId: Int? = null,
    isChecked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit
) {
    val resources = LocalContext.current.resources
    val switchText = if (textResId != null) resources.getString(textResId) else text
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(34.dp)
            .padding(top = 2.dp, bottom = 2.dp, end = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = switchText.orEmpty(),
            color = textColor,
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f)
        )
        CustomSwitch(
            checked = isChecked,
            onCheckedChange = { onCheckedChange.invoke(it) }
        )
    }
}


//thanks SemicolonSpace
@Composable
private fun CustomSwitch(
    scale: Float = 2f,
    width: Dp = 24.dp,
    height: Dp = 15.dp,
    strokeWidth: Dp = 0.dp,
    checked: Boolean = false,
    checkedThumbColor: Color = Color.White,
    checkedTrackColor: Color = mainColor,
    uncheckedThumbsColor: Color = uncheckedThumbColor,
    uncheckedTrackColor: Color = bottomBarUnselectedContentColor,
    onCheckedChange: (Boolean) -> Unit = {},
    gapBetweenThumbAndTrackEdge: Dp = 2.dp
) {

//    val switchON = remember {
//        mutableStateOf(checked) // Initially the switch is ON
//    }

//    onCheckedChange.invoke(switchON.value)

    val thumbRadius = (height / 2) - gapBetweenThumbAndTrackEdge

    // To move thumb, we need to calculate the position (along x axis)
    val animatePosition = animateFloatAsState(
        targetValue = if (checked)
            with(LocalDensity.current) { (width - thumbRadius - gapBetweenThumbAndTrackEdge).toPx() }
        else
            with(LocalDensity.current) { (thumbRadius + gapBetweenThumbAndTrackEdge).toPx() }
    )

    Canvas(
        modifier = Modifier
            .size(width = width, height = height)
            .scale(scale = scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        // This is called when the user taps on the canvas
                        onCheckedChange.invoke(!checked)
                    }
                )
            }
    ) {
        // Track
        drawRoundRect(
            color = if (checked) checkedTrackColor else uncheckedTrackColor,
            cornerRadius = CornerRadius(x = 10.dp.toPx(), y = 10.dp.toPx()),
//            style = Stroke(width = strokeWidth.toPx())
        )

        // Thumb
        drawCircle(
            color = if (checked) checkedThumbColor else uncheckedThumbsColor,
            radius = thumbRadius.toPx(),
            center = Offset(
                x = animatePosition.value,
                y = size.height / 2
            )
        )
    }

    Spacer(modifier = Modifier.height(18.dp))
}

@Preview(showBackground = true)
@Composable
fun SwitchWithTextPreview() {
    SwitchWithText(
        text = "Какой-то текст",
        isChecked = true,
        onCheckedChange = {}
    )
}