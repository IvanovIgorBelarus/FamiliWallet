package com.example.data.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.data.R

val fonts = FontFamily(
    Font(R.font.opensans_medium, weight = FontWeight.Medium),
    Font(R.font.opensans_bold, weight = FontWeight.Bold),
    Font(R.font.opensans_semibold, weight = FontWeight.SemiBold),
    Font(R.font.opensans_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.opensans_regular, weight = FontWeight.Normal),
    Font(R.font.opensans_light, weight = FontWeight.Light),
)

// Set of Material typography styles to start with
val Typography = Typography(defaultFontFamily = fonts)