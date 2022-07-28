package com.example.familiwallet.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.familiwallet.navigation.Screen

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navigation: NavHostController
) {
    BottomAppBar(
        modifier = modifier
            .height(65.dp)
            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
        cutoutShape = CircleShape,
        elevation = 0.dp
    ) {
        BottomNav(navigation = navigation)
    }
}

@Composable
fun BottomNav(
    modifier: Modifier = Modifier,
    navigation: NavHostController
) {
    val navBackStackEntry by navigation.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    BottomNavigation(
        modifier = modifier
            .height(100.dp),
        elevation = 0.dp
    ) {
        BottomNavigationItem(
            selected = currentRoute?.hierarchy?.any { it.route == Screen.MainScreen.route } == true,
            icon = {
                Icon(
                    imageVector = Screen.MainScreen.icon,
                    contentDescription = "",
                    modifier = modifier.size(36.dp)
                )
            },
            onClick = { navigation.navigate(Screen.MainScreen.route) }
        )
        BottomNavigationItem(
            selected = currentRoute?.hierarchy?.any { it.route == Screen.StatisticsScreen.route } == true,
            icon = {
                Icon(
                    imageVector = Screen.StatisticsScreen.icon,
                    contentDescription = "",
                    modifier = modifier
                        .size(36.dp)
//                        .padding(0.dp,0.dp,8.dp,0.dp)
                )
            },
            onClick = { navigation.navigate(Screen.StatisticsScreen.route) }
        )
        Spacer(modifier = Modifier.size(100.dp))
        BottomNavigationItem(
            selected = currentRoute?.hierarchy?.any { it.route == Screen.NewIncomeScreen.route } == true,
            icon = {
                Icon(
                    imageVector = Screen.NewIncomeScreen.icon,
                    contentDescription = "",
                    modifier = modifier
                        .size(36.dp)
//                        .padding(8.dp,0.dp,0.dp,0.dp)
                )
            },
            onClick = { navigation.navigate(Screen.NewIncomeScreen.route) }
        )
        BottomNavigationItem(
            selected = currentRoute?.hierarchy?.any { it.route == Screen.NewExpanseScreen.route } == true,
            icon = {
                Icon(
                    imageVector = Screen.NewExpanseScreen.icon,
                    contentDescription = "",
                    modifier = modifier.size(36.dp)
                )
            },
            onClick = { navigation.navigate(Screen.NewExpanseScreen.route) }
        )
    }
}
