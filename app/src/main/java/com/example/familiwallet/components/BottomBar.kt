package com.example.familiwallet.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.familiwallet.navigation.Screen
import com.example.familiwallet.ui.theme.bottomBarBackgroundColor
import com.example.familiwallet.ui.theme.bottomBarContentColor
import com.example.familiwallet.ui.theme.bottomBarSelectedContentColor
import com.example.familiwallet.ui.theme.bottomBarUnselectedContentColor
import com.example.familiwallet.ui.theme.expensesBackgroundColor
import com.example.familiwallet.ui.theme.expensesColor

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
        backgroundColor = bottomBarBackgroundColor,
        contentColor = Color.White,
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
        backgroundColor = bottomBarBackgroundColor,
        contentColor = bottomBarContentColor,
        elevation = 0.dp
    ) {
        BottomNavigationItem(
            selected = currentRoute?.hierarchy?.any { it.route == Screen.StartScreen.route } == true,
            selectedContentColor = bottomBarSelectedContentColor,
            unselectedContentColor = bottomBarUnselectedContentColor,
            icon = {
                Icon(
                    painter = painterResource(id = Screen.StartScreen.icon),
                    contentDescription = "",
                    modifier = modifier.size(36.dp)
                )
            },
            onClick = { navigation.navigate(Screen.StartScreen.route) }
        )
        BottomNavigationItem(
            selected = currentRoute?.hierarchy?.any { it.route == Screen.CategoryScreen.route } == true,
            selectedContentColor = bottomBarSelectedContentColor,
            unselectedContentColor = bottomBarUnselectedContentColor,
            icon = {
                Icon(
                    painter = painterResource(id = Screen.CategoryScreen.icon),
                    contentDescription = "",
                    modifier = modifier
                        .size(36.dp)
                )
            },
            onClick = { navigation.navigate(Screen.CategoryScreen.route) }
        )
        Spacer(modifier = Modifier.size(100.dp))
        BottomNavigationItem(
            selected = currentRoute?.hierarchy?.any { it.route == Screen.HistoryScreen.route } == true,
            selectedContentColor = bottomBarSelectedContentColor,
            unselectedContentColor = bottomBarUnselectedContentColor,
            icon = {
                Icon(
                    painter = painterResource(id = Screen.HistoryScreen.icon),
                    contentDescription = "",
                    modifier = modifier
                        .size(36.dp)
                )
            },
            onClick = { navigation.navigate(Screen.HistoryScreen.route) }
        )
        BottomNavigationItem(
            selected = currentRoute?.hierarchy?.any { it.route == Screen.SettingsScreen.route } == true,
            selectedContentColor = bottomBarSelectedContentColor,
            unselectedContentColor = bottomBarUnselectedContentColor,
            icon = {
                Icon(
                    painter = painterResource(id = Screen.SettingsScreen.icon),
                    contentDescription = "",
                    modifier = modifier.size(36.dp)
                )
            },
            onClick = { navigation.navigate(Screen.SettingsScreen.route) }
        )
    }
}
