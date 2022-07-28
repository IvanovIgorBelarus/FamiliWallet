package com.example.familiwallet.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.familiwallet.navigation.Screen
import com.example.familiwallet.ui.theme.Purple200
import com.example.familiwallet.ui.theme.expensesColor
import com.example.familiwallet.ui.theme.incomesColor

enum class FloatingActionState {
    EXPANDED,
    COLLAPSED
}

@Composable
fun ActionButton(
    floatingActionState: FloatingActionState,
    navigation: NavHostController = rememberNavController(),
    onStateChange: (FloatingActionState) -> Unit
) {
    val animSize by animateDpAsState(targetValue = if (floatingActionState == FloatingActionState.COLLAPSED) 0.dp else 96.dp) //for child buttons
    val animSizeSpacer by animateDpAsState(targetValue = if (floatingActionState == FloatingActionState.COLLAPSED) 0.dp else 24.dp) //for Spacer
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChildActionButton(icon = Icons.Default.KeyboardArrowUp, color = incomesColor, animSize) { navigation.navigate(Screen.NewIncomeScreen.route) }
            Spacer(modifier = Modifier.size(animSizeSpacer))
            ActionButtonView(
                floatingActionState = floatingActionState,
                onStateChange = onStateChange
            )
            Spacer(modifier = Modifier.size(animSizeSpacer))
            ChildActionButton(icon = Icons.Default.KeyboardArrowDown, color = expensesColor, animSize) { navigation.navigate(Screen.NewExpanseScreen.route) }
        }
    }
}

@Composable
private fun ActionButtonView(
    floatingActionState: FloatingActionState,
    onStateChange: (FloatingActionState) -> Unit
) {
    val transition = updateTransition(targetState = floatingActionState, label = "transition")
    val rotate by transition.animateFloat(label = "rotate") {
        if (it == FloatingActionState.EXPANDED) 315f else 0f
    }
    val animSize by animateDpAsState(targetValue = if (floatingActionState == FloatingActionState.EXPANDED) 48.dp else 60.dp)
    FloatingActionButton(
        onClick = {
            onStateChange(
                if (transition.currentState == FloatingActionState.EXPANDED) FloatingActionState.COLLAPSED else FloatingActionState.EXPANDED
            )
        },
        modifier = Modifier
            .size(animSize),
        backgroundColor = Purple200
    ) {
        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.rotate(rotate))
    }
}

@Composable
private fun ChildActionButton(icon: ImageVector, color: Color, size: Dp, onclickListener: () -> Unit = {}) {
    FloatingActionButton(
        onClick = { onclickListener() },
        modifier = Modifier.size(size),
        backgroundColor = color
    ) {
        Icon(icon, contentDescription = null)
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionButtonPreview() {
    ActionButton(
        floatingActionState = FloatingActionState.EXPANDED,
        navigation = rememberNavController(),
        onStateChange = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ActionButtonViewPreview() {
    ActionButtonView(
        floatingActionState = FloatingActionState.COLLAPSED,
        onStateChange = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ChildActionButtonPreview() {
    ChildActionButton(icon = Icons.Default.KeyboardArrowUp, color = incomesColor, 60.dp) { }
}