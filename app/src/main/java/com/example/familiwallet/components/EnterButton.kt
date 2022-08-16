package com.example.familiwallet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.familiwallet.R
import com.example.familiwallet.core.utils.UserUtils
import com.example.familiwallet.navigation.Screen
import com.example.familiwallet.ui.theme.backgroundColor
import com.example.familiwallet.ui.theme.gradColor1
import com.example.familiwallet.ui.theme.gradColor2
import com.example.familiwallet.ui.theme.gradColor3

@Composable
fun EnterButton(
    modifier: Modifier = Modifier,
    text:Int = R.string.enter,
    onClick: ()->Unit = {}
) {
    val resources = LocalContext.current.resources

    Box(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = backgroundColor),
            ) {
                onClick.invoke()
            }
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
                text = resources.getString(text),
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