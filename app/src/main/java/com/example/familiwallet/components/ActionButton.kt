package com.example.familiwallet.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.familiwallet.ui.theme.bottomBarContentColor
import com.example.familiwallet.ui.theme.mainColor

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    navigation: NavHostController? = null
) {
    FloatingActionButton(
        onClick = {

        },
        modifier = modifier
            .size(60.dp),
        backgroundColor = bottomBarContentColor
    ) {
        Icon(
            Icons.Rounded.Add,
            contentDescription = null,
            tint = Color.White,
            modifier = modifier.size(36.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionButtonPreview() {
    ActionButton()
}
