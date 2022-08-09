package com.example.familiwallet.features.enterscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.example.familiwallet.R
import com.example.familiwallet.ui.theme.backgroundColor
import com.example.familiwallet.ui.theme.enterTextColor
import com.example.familiwallet.ui.theme.gradColor1
import com.example.familiwallet.ui.theme.gradColor2
import com.example.familiwallet.ui.theme.gradColor3

@Composable
fun EnterScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController? = null
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        backgroundColor = backgroundColor
    ) {
        ConstraintLayout(modifier = modifier.fillMaxSize()) {
            val (image, text, button) = createRefs()

            Image(
                modifier = modifier
                    .fillMaxWidth()
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                painter = painterResource(id = R.drawable.ic_enter_screen),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Text(
                text = "Добро пожаловать в приложение Money",
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
                    .constrainAs(text) {
                        top.linkTo(image.bottom, margin = 48.dp)
                    },
                fontSize = 30.sp,
                color = enterTextColor,
                fontWeight = FontWeight.W500
            )
            Box(
                modifier = Modifier
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom, margin = 60.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        width = Dimension.fillToConstraints
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
                        text = "Вход",
                        fontSize = 22.sp,
                        color = Color.White,
                        fontWeight = FontWeight.W500
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .background(color = Color.White, CircleShape)
                            .size(42.dp)
                    ) {}
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EnterScreenPreview() {
    EnterScreen()
}