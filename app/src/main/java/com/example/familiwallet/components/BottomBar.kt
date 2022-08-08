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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.familiwallet.navigation.Screen
import com.example.familiwallet.ui.theme.bottomBarBackgroundColor
import com.example.familiwallet.ui.theme.bottomBarContentColor
import com.example.familiwallet.ui.theme.bottomBarSelectedContentColor
import com.example.familiwallet.ui.theme.bottomBarUnselectedContentColor

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
            selected = isSelectedItem(currentRoute, Screen.StartScreen.route),
            selectedContentColor = bottomBarSelectedContentColor,
            unselectedContentColor = bottomBarUnselectedContentColor,
            icon = {
                Icon(
                    painter = painterResource(id = Screen.StartScreen.icon),
                    contentDescription = "",
                    modifier = modifier.size(36.dp)
                )
            },
            onClick = { if (!isSelectedItem(currentRoute, Screen.StartScreen.route)) navigation.navigate(Screen.StartScreen.route) },
            label = { setLabel(title = Screen.StartScreen.title, isSelected = isSelectedItem(currentRoute, Screen.StartScreen.route)) }
        )
        BottomNavigationItem(
            selected = isSelectedItem(currentRoute, Screen.CategoryScreen.route),
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
            onClick = { if (!isSelectedItem(currentRoute, Screen.CategoryScreen.route)) navigation.navigate(Screen.CategoryScreen.route) },
            label = { setLabel(title = Screen.CategoryScreen.title, isSelected = isSelectedItem(currentRoute, Screen.CategoryScreen.route)) }
        )
        Spacer(modifier = Modifier.size(60.dp))
        BottomNavigationItem(
            selected = isSelectedItem(currentRoute, Screen.HistoryScreen.route),
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
            onClick = { if (!isSelectedItem(currentRoute, Screen.HistoryScreen.route)) navigation.navigate(Screen.HistoryScreen.route) },
            label = { setLabel(title = Screen.HistoryScreen.title, isSelected = isSelectedItem(currentRoute, Screen.HistoryScreen.route)) }
        )
        BottomNavigationItem(
            selected = isSelectedItem(currentRoute, Screen.SettingsScreen.route),
            selectedContentColor = bottomBarSelectedContentColor,
            unselectedContentColor = bottomBarUnselectedContentColor,
            icon = {
                Icon(
                    painter = painterResource(id = Screen.SettingsScreen.icon),
                    contentDescription = "",
                    modifier = modifier.size(36.dp)
                )
            },
            onClick = { if (!isSelectedItem(currentRoute, Screen.SettingsScreen.route)) navigation.navigate(Screen.SettingsScreen.route) },
            label = { setLabel(title = Screen.SettingsScreen.title, isSelected = isSelectedItem(currentRoute, Screen.SettingsScreen.route)) }
        )
    }
}

private fun isSelectedItem(currentRoute: NavDestination?, targetRoute: String): Boolean {
    return currentRoute?.hierarchy?.any { it.route == targetRoute } == true
}

@Composable
private fun setLabel(title: String?, isSelected: Boolean) {
    val textColor = if (isSelected) bottomBarSelectedContentColor else bottomBarUnselectedContentColor
    Text(text = title.orEmpty(), color = textColor, fontSize = 10.sp)
}

