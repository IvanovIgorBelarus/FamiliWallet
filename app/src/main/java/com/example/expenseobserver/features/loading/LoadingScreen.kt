package com.example.expenseobserver.features.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.expenseobserver.core.common.noRippleClickable
import com.example.expenseobserver.ui.theme.bottomBarContentColor
import com.example.expenseobserver.ui.theme.textColor

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier
            .fillMaxSize()
            .background(textColor.copy(alpha = 0.1f))
            .noRippleClickable { }
            .focusable(true)
    ) {
        CircularProgressIndicator(color = bottomBarContentColor)
    }
}


