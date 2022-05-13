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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.familiwallet.ui.theme.Purple200
import com.example.familiwallet.ui.theme.Purple500
import com.example.familiwallet.ui.theme.Purple700

enum class FloatingActionState {
    EXPANDED,
    COLLAPSED
}

@Composable
fun ActionButton(
    floatingActionState: FloatingActionState,
    onStateChange: (FloatingActionState) -> Unit
) {
    val transition = updateTransition(targetState = floatingActionState, label = "transition")
    val animSize by animateDpAsState(targetValue = if (floatingActionState == FloatingActionState.COLLAPSED) 0.dp else 60.dp)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChildActionButton(icon = Icons.Default.Add, color = Purple500, animSize)
            Spacer(modifier = Modifier.size(96.dp))
            ChildActionButton(icon = Icons.Default.Delete, color = Purple700, animSize)
        }

        Spacer(modifier = Modifier.size(24.dp))
        ActionButtonView(
            transition = transition,
            onStateChange = onStateChange
        )
    }
}

@Composable
private fun ActionButtonView(
    transition: Transition<FloatingActionState>,
    onStateChange: (FloatingActionState) -> Unit
) {
    val rotate by transition.animateFloat(label = "rotate") {
        if (it == FloatingActionState.EXPANDED) 315f else 0f
    }

    FloatingActionButton(
        onClick = {
            onStateChange(
                if (transition.currentState == FloatingActionState.EXPANDED) FloatingActionState.COLLAPSED else FloatingActionState.EXPANDED
            )
        },
        modifier = Modifier
            .size(75.dp),
        backgroundColor = Purple200
    ) {
        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.rotate(rotate))
    }
}

@Composable
private fun ChildActionButton(icon: ImageVector, color: Color, size: Dp) {
    FloatingActionButton(
        onClick = {
        },
        modifier = Modifier.size(size),
        backgroundColor = color
    ) {
        Icon(icon, contentDescription = null)
    }
}