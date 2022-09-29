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
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
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
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        modifier = modifier
            .height(100.dp),
        backgroundColor = bottomBarBackgroundColor,
        contentColor = bottomBarContentColor,
        elevation = 0.dp
    ) {
        listOfNavItems.forEach { screen ->
            BottomNavigationItem(
                selected = Screen.getScreen(currentRoute).stack == screen.stack,
                selectedContentColor = bottomBarSelectedContentColor,
                unselectedContentColor = bottomBarUnselectedContentColor,
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = "",
                        modifier = modifier.size(36.dp)
                    )
                },
                onClick = { onNavItemClick(navigation, screen.route, currentRoute) },
                label = { setLabel(title = screen.title, isSelected = Screen.getScreen(currentRoute).stack == screen.stack) }
            )
            if (screen is Screen.CategoryScreen) {
                Spacer(modifier = Modifier.size(60.dp))
            }
        }
    }
}

private fun onNavItemClick(
    navigation: NavHostController,
    route: String,
    currentRoute: String?
) {
    if (route != currentRoute) {
        navigation.navigate(route) {
            launchSingleTop = true
            restoreState = true
            popUpTo(findStartDestination(navigation.graph).id)
        }
    }
}

tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.findStartDestination()) else graph
}

@Composable
private fun setLabel(title: String?, isSelected: Boolean) {
    val textColor = if (isSelected) bottomBarSelectedContentColor else bottomBarUnselectedContentColor
    Text(text = title.orEmpty(), color = textColor, fontSize = 10.sp)
}

private val listOfNavItems = listOf(
    Screen.StartScreen,
    Screen.CategoryScreen,
    Screen.HistoryScreen,
    Screen.SettingsScreen
)

