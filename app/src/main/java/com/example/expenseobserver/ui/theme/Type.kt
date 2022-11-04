package com.example.expenseobserver.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.expenseobserver.R

val fonts = FontFamily(
    Font(R.font.raleway_medium, weight = FontWeight.Medium),
    Font(R.font.raleway_bold, weight = FontWeight.Bold),
    Font(R.font.raleway_semibold, weight = FontWeight.SemiBold),
    Font(R.font.raleway_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.raleway_extralight, weight = FontWeight.ExtraLight),
    Font(R.font.raleway_regular, weight = FontWeight.Normal),
    Font(R.font.raleway_light, weight = FontWeight.Light),
    Font(R.font.raleway_thin, weight = FontWeight.Thin),
)

// Set of Material typography styles to start with
val Typography = Typography()