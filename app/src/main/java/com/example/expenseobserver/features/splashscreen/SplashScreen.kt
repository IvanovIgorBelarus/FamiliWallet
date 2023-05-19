package com.example.expenseobserver.features.splashscreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.navigation.Screen

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    durationMillis: Int = 1000,
    startAnimSize: Dp = 0.dp
) {
    Surface {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            var targetAnimSize by remember { mutableStateOf(startAnimSize) }
            val animSize by animateDpAsState(
                targetValue = targetAnimSize,
                animationSpec = tween(durationMillis),
                finishedListener = {
                    navigation.popBackStack()
                    navigation.navigate(Screen.EnterScreen.route)
                }
            )
            Image(
                painter = painterResource(id = Screen.SplashScreen.icon),
                contentDescription = "",
                modifier = Modifier.size(animSize)
            )
            LaunchedEffect(Unit) {
                targetAnimSize = 64.dp
            }
        }
    }
}