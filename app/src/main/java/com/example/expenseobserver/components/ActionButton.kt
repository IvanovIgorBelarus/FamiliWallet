package com.example.expenseobserver.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.expenseobserver.ui.theme.bottomBarContentColor
import com.example.expenseobserver.ui.theme.mainColor

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = {
            onClick.invoke()
        },
//        modifier = modifier
//            .size(60.dp),
        contentColor = bottomBarContentColor,
        containerColor = mainColor,
        shape = CircleShape
    ) {
        Icon(
            Icons.Rounded.Add,
            contentDescription = null,
            tint = Color.White,
            modifier = modifier.size(36.dp)
        )
    }
}
