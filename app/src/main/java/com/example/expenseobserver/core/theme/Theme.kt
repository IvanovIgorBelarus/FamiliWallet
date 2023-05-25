package com.example.expenseobserver.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.example.data.theme.Purple200
import com.example.data.theme.Purple700
import com.example.data.theme.Shapes
import com.example.data.theme.Teal200
import com.example.data.theme.Typography
import com.example.data.theme.backgroundColor
import com.example.data.theme.bottomBarContentColor

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = bottomBarContentColor,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = backgroundColor,
    surface = backgroundColor
)

@Composable
fun FamiliWalletTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
//    val colors = if (darkTheme) {
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }

    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}