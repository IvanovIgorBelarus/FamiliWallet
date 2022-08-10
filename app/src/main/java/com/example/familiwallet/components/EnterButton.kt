package com.example.familiwallet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.familiwallet.R
import com.example.familiwallet.ui.theme.gradColor1
import com.example.familiwallet.ui.theme.gradColor2
import com.example.familiwallet.ui.theme.gradColor3

@Composable
fun EnterButton(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(64.dp)
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        gradColor1, gradColor2, gradColor3, gradColor3
                    )
                ),
                RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "Вход",
                fontSize = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.W500
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .background(color = Color.White, CircleShape)
                    .size(42.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_24), contentDescription = null, tint = gradColor3)
            }
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EnterButtonPreview(){
    EnterButton()
}