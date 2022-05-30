package com.example.familiwallet.components

import androidx.compose.animation.core.Transition
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.familiwallet.navigation.Screen
import com.example.familiwallet.ui.theme.*

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
    val transition = updateTransition(targetState = floatingActionState, label = "transition")
    val animSize by animateDpAsState(targetValue = if (floatingActionState == FloatingActionState.COLLAPSED) 0.dp else 96.dp)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChildActionButton(icon = Icons.Default.KeyboardArrowUp, color = incomesColor, animSize) { navigation.navigate(Screen.NewIncomeScreen.route) }
            Spacer(modifier = Modifier.size(96.dp))
            ChildActionButton(icon = Icons.Default.KeyboardArrowDown, color = expensesColor, animSize) { navigation.navigate(Screen.NewExpanseScreen.route) }
        }

        Spacer(modifier = Modifier.size(24.dp))
        ActionButtonView(
            transition = transition,
            floatingActionState = floatingActionState,
            onStateChange = onStateChange
        )
    }
}

@Composable
private fun ActionButtonView(
    transition: Transition<FloatingActionState>,
    floatingActionState: FloatingActionState,
    onStateChange: (FloatingActionState) -> Unit
) {
    val rotate by transition.animateFloat(label = "rotate") {
        if (it == FloatingActionState.EXPANDED) 315f else 0f
    }
    val animSize by animateDpAsState(targetValue = if (floatingActionState == FloatingActionState.EXPANDED) 60.dp else 96.dp)
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