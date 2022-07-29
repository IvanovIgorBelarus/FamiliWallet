package com.example.familiwallet.core.utils

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.familiwallet.R

enum class AppIcons(val imageRes: Int, val actionType: IconActionType) {
    AIRLINE_SEAT_INDIVIDUAL_SUITE(R.drawable.ic_airline_seat_individual_suite, IconActionType.HOBBY),
    BEACH_ACCESS(R.drawable.ic_beach_access, IconActionType.HOBBY),
    BRUSH(R.drawable.ic_brush, IconActionType.HOBBY),
    FITNESS_CENTER(R.drawable.ic_fitness_center, IconActionType.HOBBY),
    PALETTE(R.drawable.ic_palette, IconActionType.HOBBY),
}
enum class IconActionType{
    HOBBY
}

@Preview(showBackground = true)
@Composable
private fun showIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_brush),
        contentDescription = "",
        tint = Color.Cyan
    )
}
