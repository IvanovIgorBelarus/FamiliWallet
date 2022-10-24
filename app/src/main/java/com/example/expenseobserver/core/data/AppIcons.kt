package com.example.expenseobserver.core.data

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.expenseobserver.R

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
    FAST_FOOD(R.drawable.ic_fastfood, IconActionType.FOOD),
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

    ACCOUNT_BALANCE(R.drawable.ic_account_balance, IconActionType.HOME),
    ACCOUNT_BALANCE_WALLET(R.drawable.ic_account_balance_wallet, IconActionType.HOME),
    ARMCHAIR(R.drawable.ic_armchair, IconActionType.HOME),
    BACKPACK(R.drawable.ic_backpack, IconActionType.HOME),
    BUSINESS_CENTER(R.drawable.ic_backpack, IconActionType.HOME),
    CARD_MEMBERSHIP(R.drawable.ic_card_membership, IconActionType.HOME),
    CART(R.drawable.ic_cart, IconActionType.HOME),
    CASINO(R.drawable.ic_casino, IconActionType.HOME),
    CREDIT(R.drawable.ic_credit, IconActionType.HOME),
    DEVICES(R.drawable.ic_devices, IconActionType.HOME),
    DOMAIN(R.drawable.ic_domain, IconActionType.HOME),
    EVENT_SEAT(R.drawable.ic_event_seat, IconActionType.HOME),
    FORMAT_PAINT(R.drawable.ic_format_paint, IconActionType.HOME),
    FRAME44(R.drawable.ic_frame_44, IconActionType.HOME),
    FRAME45(R.drawable.ic_frame_45, IconActionType.HOME),
    HOME(R.drawable.ic_home, IconActionType.HOME),
    INSURANCE(R.drawable.ic_insurance, IconActionType.HOME),
    KITCHEN(R.drawable.ic_kitchen, IconActionType.HOME),
    LANGUAGE(R.drawable.ic_language, IconActionType.HOME),
    LAPTOP(R.drawable.ic_laptop, IconActionType.HOME),
    LAPTOP_CHROMEBOOK(R.drawable.ic_laptop_chromebook, IconActionType.HOME),
    LAPTOP_MAC(R.drawable.ic_laptop_mac, IconActionType.HOME),
    LOCAL_GROCERY_STORE(R.drawable.ic_local_grocery_store, IconActionType.HOME),
    LOCAL_HOSPITAL(R.drawable.ic_local_hospital, IconActionType.HOME),
    LOCAL_LAUNDRY_SERVICE(R.drawable.ic_local_laundry_service, IconActionType.HOME),
    LOCAL_MALL(R.drawable.ic_local_mall, IconActionType.HOME),
    LOCAL_PHONE(R.drawable.ic_local_phone, IconActionType.HOME),
    LOCAL_PLAY(R.drawable.ic_local_play, IconActionType.HOME),
    LOCAL_PRINT_SHOP(R.drawable.ic_local_printshop, IconActionType.HOME),
    LOCAL_SEE(R.drawable.ic_local_see, IconActionType.HOME),
    LOCATION_CITY(R.drawable.ic_location_city, IconActionType.HOME),
    LOCK_KEY_OPEN(R.drawable.ic_lockkeyopen, IconActionType.HOME),
    MOUSE(R.drawable.ic_mouse, IconActionType.HOME),
    PHONE(R.drawable.ic_phone, IconActionType.HOME),
    RESOURCE_PUBLIC(R.drawable.ic_resource_public, IconActionType.HOME),
    ROOM_SERVICE(R.drawable.ic_room_service, IconActionType.HOME),
    SETTINGS_VOICE(R.drawable.ic_settings_voice, IconActionType.HOME),
    SHOPPING_BASKET(R.drawable.ic_shopping_basket, IconActionType.HOME),
    SHOPPING_BASKET_1(R.drawable.ic_shopping_basket_1, IconActionType.HOME),
    SPEAKER(R.drawable.ic_speaker, IconActionType.HOME),
    STORE_MALL_DIRECTORY(R.drawable.ic_store_mall_directory, IconActionType.HOME),
    WEEKEND(R.drawable.ic_weekend, IconActionType.HOME),

    BARBELL(R.drawable.ic_barbell, IconActionType.OTHER),
    BEAUTY_SALON(R.drawable.ic_beauty_salon, IconActionType.OTHER),
    BRAIN(R.drawable.ic_brain, IconActionType.OTHER),
    CARD_GIFT_CARD(R.drawable.ic_card_giftcard, IconActionType.OTHER),
    CHARITY(R.drawable.ic_charity, IconActionType.OTHER),
    ENTERTAINMENT(R.drawable.ic_entertainment, IconActionType.OTHER),
    FILM(R.drawable.ic_film, IconActionType.OTHER),
    FILTER_VINTAGE(R.drawable.ic_filter_vintage, IconActionType.OTHER),
    FRAME49(R.drawable.ic_frame_49, IconActionType.OTHER),
    FRAME50(R.drawable.ic_frame_50, IconActionType.OTHER),
    FRAME51(R.drawable.ic_frame_51, IconActionType.OTHER),
    GAMES(R.drawable.ic_games, IconActionType.OTHER),
    LOCAL_FLORIST(R.drawable.ic_local_florist, IconActionType.OTHER),
    MEDICINE(R.drawable.ic_medicine, IconActionType.OTHER),
    MOTOR_SPORTS(R.drawable.ic_motorsports, IconActionType.OTHER),
    MUSIC_NOTES(R.drawable.ic_musicnotes, IconActionType.OTHER),
    SETTINGS_INPUT_S_VIDEO(R.drawable.ic_settings_input_svideo, IconActionType.OTHER),
    SPA(R.drawable.ic_spa, IconActionType.OTHER),
    SPORT_AMERICAN_FOOTBALL(R.drawable.ic_sport_american_football, IconActionType.OTHER),
    SPORT_FOOTBALL(R.drawable.ic_sport_football, IconActionType.OTHER),
    T_SHIRT(R.drawable.ic_t_shirt, IconActionType.OTHER),
    TROPHY(R.drawable.ic_trophy, IconActionType.OTHER),

    BOY(R.drawable.ic_boy, IconActionType.PEOPLE),
    CHILD_CARE(R.drawable.ic_child_care, IconActionType.PEOPLE),
    CHILD_FRIENDLY(R.drawable.ic_child_friendly, IconActionType.PEOPLE),
    DIRECTIONS_BIKE(R.drawable.ic_directions_bike, IconActionType.PEOPLE),
    DIRECTIONS_RUN(R.drawable.ic_directions_run, IconActionType.PEOPLE),
    GIRL(R.drawable.ic_girl, IconActionType.PEOPLE),
    PERM_IDENTITY(R.drawable.ic_perm_identity, IconActionType.PEOPLE),
    PERSON_ADD(R.drawable.ic_person_add, IconActionType.PEOPLE),
    POOL(R.drawable.ic_pool, IconActionType.PEOPLE),
    PREGNANT_WOMAN(R.drawable.ic_pregnant_woman, IconActionType.PEOPLE),
    ROWING(R.drawable.ic_rowing, IconActionType.PEOPLE),
    SELLING_THINGS(R.drawable.ic_selling_things, IconActionType.PEOPLE),
    SUPERVISOR_ACCOUNT(R.drawable.ic_supervisor_account, IconActionType.PEOPLE),
    TRANSFER_WITHIN_A_STATION(R.drawable.ic_transfer_within_a_station, IconActionType.PEOPLE),
    WC(R.drawable.ic_wc, IconActionType.PEOPLE),

    AIRPLANE_MODE_ACTIVE(R.drawable.ic_airplanemode_active, IconActionType.TRANSPORT),
    AIRPORT_SHUTTLE(R.drawable.ic_airport_shuttle, IconActionType.TRANSPORT),
    CLARITY_AIRPLANE_SOLID(R.drawable.ic_clarity_airplane_solid, IconActionType.TRANSPORT),
    DIRECTIONS_BOAT(R.drawable.ic_directions_boat, IconActionType.TRANSPORT),
    DIRECTIONS_BUS(R.drawable.ic_directions_bus, IconActionType.TRANSPORT),
    DIRECTIONS_CAR(R.drawable.ic_directions_car, IconActionType.TRANSPORT),
    DIRECTIONS_RAILWAY(R.drawable.ic_directions_railway, IconActionType.TRANSPORT),
    DIRECTIONS_SUBWAY(R.drawable.ic_directions_subway, IconActionType.TRANSPORT),
    DRIVE_ETA(R.drawable.ic_drive_eta, IconActionType.TRANSPORT),
    EV_STATION(R.drawable.ic_ev_station, IconActionType.TRANSPORT),
    FLIGHT_LAND(R.drawable.ic_flight_land, IconActionType.TRANSPORT),
    FLIGHT_TAKE_OFF(R.drawable.ic_flight_takeoff, IconActionType.TRANSPORT),
    FLY(R.drawable.ic_fly, IconActionType.TRANSPORT),
    GAS_PUMP(R.drawable.ic_gaspump, IconActionType.TRANSPORT),
    LOCAL_GAS_STATION(R.drawable.ic_local_gas_station, IconActionType.TRANSPORT),
    LOCAL_SHIPPING(R.drawable.ic_local_shipping, IconActionType.TRANSPORT),
    LOCAL_TAXI(R.drawable.ic_local_taxi, IconActionType.TRANSPORT),
    MOTORCYCLE(R.drawable.ic_motorcycle, IconActionType.TRANSPORT),
    SUBWAY(R.drawable.ic_subway, IconActionType.TRANSPORT),
    TRAIN(R.drawable.ic_train, IconActionType.TRANSPORT),
    TRAM(R.drawable.ic_tram, IconActionType.TRANSPORT),

    PLUS(R.drawable.ic_add_category, IconActionType.UNKNOWN),
    UNKNOWN(R.drawable.ic_baseline_insert_photo, IconActionType.UNKNOWN);

    companion object {
        fun getImageRes(name: String?): AppIcons {
            return values().firstOrNull { it.name == name } ?: UNKNOWN
        }
    }
}

enum class IconActionType(val titleRes: Int) {
    HOBBY(R.string.hobby),
    ANIMALS(R.string.pets),
    FOOD(R.string.food),
    HOME(R.string.home),
    OTHER(R.string.other),
    PEOPLE(R.string.people),
    TRANSPORT(R.string.transport),
    UNKNOWN(-1);
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
