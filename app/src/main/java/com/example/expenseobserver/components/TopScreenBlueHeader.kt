package com.example.expenseobserver.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expenseobserver.ui.theme.backgroundColor
import com.example.expenseobserver.ui.theme.buttonColor

@Composable
fun TopScreenBlueHeader(
    modifier: Modifier = Modifier,
    text: String
) {
    val topHeaderHeight = LocalConfiguration.current.screenHeightDp.dp/3
    Box(
        modifier
            .fillMaxWidth()
            .height(topHeaderHeight)
            .background(color = buttonColor, RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
    ) {
        Text(
            text = text,
            Modifier
                .fillMaxWidth()
                .padding(top = topHeaderHeight/7),
            fontSize = 36.sp,
            color = backgroundColor,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TopScreenBlueHeaderPreview() {
    TopScreenBlueHeader(Modifier, "Заголовок")
}