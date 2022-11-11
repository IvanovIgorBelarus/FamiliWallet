package com.example.expenseobserver.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.example.expenseobserver.navigation.Screen
import com.example.expenseobserver.ui.theme.bottomBarBackgroundColor
import com.example.expenseobserver.ui.theme.bottomBarContentColor
import com.example.expenseobserver.ui.theme.bottomBarSelectedContentColor
import com.example.expenseobserver.ui.theme.bottomBarUnselectedContentColor
import com.example.expenseobserver.ui.theme.textColor

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navigation: NavHostController
) {
    val navBackStackEntry by navigation.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar(
        modifier = modifier
            .height(65.dp)
            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
//        cutoutShape = CircleShape,  //planned
        containerColor = bottomBarBackgroundColor,
        contentColor = Color.White,
        tonalElevation = 0.dp
    ) {
        listOfNavItems.forEach { screen ->
            IconButton(
                onClick = { onNavItemClick(navigation, screen.route, currentRoute) },
                modifier = Modifier.weight(1f),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = if (Screen.getScreen(currentRoute).stack == screen.stack) bottomBarSelectedContentColor else bottomBarUnselectedContentColor
                )
            ) {
                Column(

                ) {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = "",
                        modifier = modifier.size(36.dp)
                    )
                    Text(
                        text = screen.title.orEmpty(),
                        color = if (Screen.getScreen(currentRoute).stack == screen.stack) bottomBarSelectedContentColor else bottomBarUnselectedContentColor,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

//@Composable
//fun BottomNav(
//    modifier: Modifier = Modifier,
//    navigation: NavHostController
//) {
//    val navBackStackEntry by navigation.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route
//
//    BottomNavigation(
//        modifier = modifier
//            .height(100.dp),
//        backgroundColor = bottomBarBackgroundColor,
//        contentColor = bottomBarContentColor,
//        elevation = 0.dp
//    ) {
//        listOfNavItems.forEach { screen ->
//            BottomNavigationItem(
//                selected = Screen.getScreen(currentRoute).stack == screen.stack,
//                selectedContentColor = bottomBarSelectedContentColor,
//                unselectedContentColor = bottomBarUnselectedContentColor,
//                icon = {
//                    Icon(
//                        painter = painterResource(id = screen.icon),
//                        contentDescription = "",
//                        modifier = modifier.size(36.dp)
//                    )
//                },
//                onClick = { onNavItemClick(navigation, screen.route, currentRoute) },
//                label = { setLabel(title = screen.title, isSelected = Screen.getScreen(currentRoute).stack == screen.stack) }
//            )
//            if (screen is Screen.CategoryScreen) {
//                Spacer(modifier = Modifier.size(60.dp))
//            }
//        }
//    }
//}

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

//@Composable
//private fun setLabel(title: String?, isSelected: Boolean) {
//    val textColor = if (isSelected) bottomBarSelectedContentColor else bottomBarUnselectedContentColor
//    Text(text = title.orEmpty(), color = textColor, fontSize = 10.sp)
//}

private val listOfNavItems = listOf(
    Screen.StartScreen,
    Screen.CategoryScreen,
    Screen.HistoryScreen,
    Screen.SettingsScreen
)

