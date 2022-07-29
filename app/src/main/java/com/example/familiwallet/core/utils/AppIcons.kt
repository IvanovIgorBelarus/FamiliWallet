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

    BUG_REPORT(R.drawable.ic_bug_report, IconActionType.ANIMALS),
    BUTTERFLY(R.drawable.ic_butterfly, IconActionType.ANIMALS),
    CAT(R.drawable.ic_cat, IconActionType.ANIMALS),
    DOG(R.drawable.ic_dog, IconActionType.ANIMALS),
    MDI_BIRD(R.drawable.ic_mdi_bird, IconActionType.ANIMALS),
    MDI_CAT(R.drawable.ic_mdi_cat, IconActionType.ANIMALS),
    MDI_DOG(R.drawable.ic_mdi_dog, IconActionType.ANIMALS),
    MDI_DOG_SIDE(R.drawable.ic_mdi_dog_side, IconActionType.ANIMALS),
    MDI_FISH(R.drawable.ic_mdi_fish, IconActionType.ANIMALS),
    MDI_FISHBOWL(R.drawable.ic_mdi_fishbowl, IconActionType.ANIMALS),
    MDI_FISHBOWL_OUTLINE(R.drawable.ic_mdi_fishbowl_outline, IconActionType.ANIMALS),
    MDI_LADYBUG(R.drawable.ic_mdi_ladybug, IconActionType.ANIMALS),
    MDI_SNAKE(R.drawable.ic_mdi_snake, IconActionType.ANIMALS),
    PAW(R.drawable.ic_paw, IconActionType.ANIMALS),
    PETS(R.drawable.ic_pets, IconActionType.ANIMALS),

    ALCOHOL(R.drawable.ic_alcohol, IconActionType.FOOD),
    CONTENT_CUT(R.drawable.ic_content_cut, IconActionType.FOOD),
    FASTFOOD(R.drawable.ic_fastfood, IconActionType.FOOD),
    FRAME52(R.drawable.ic_frame_52, IconActionType.FOOD),
    FRAME53(R.drawable.ic_frame_53, IconActionType.FOOD),
    LOCAL_BAR(R.drawable.ic_local_bar, IconActionType.FOOD),
    LOCAL_CAFE(R.drawable.ic_local_cafe, IconActionType.FOOD),
    LOCAL_DINING(R.drawable.ic_local_dining, IconActionType.FOOD),
    LOCAL_DRINK(R.drawable.ic_local_drink, IconActionType.FOOD),
    LOCAL_PIZZA(R.drawable.ic_local_pizza, IconActionType.FOOD),
    POPCORN(R.drawable.ic_popcorn, IconActionType.FOOD),
    RESTAURANT(R.drawable.ic_restaurant, IconActionType.FOOD),
    WINE(R.drawable.ic_wine, IconActionType.FOOD),
}

enum class IconActionType {
    HOBBY,
    ANIMALS,
    FOOD
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
