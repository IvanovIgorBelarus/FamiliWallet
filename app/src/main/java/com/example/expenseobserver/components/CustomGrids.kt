package com.example.expenseobserver.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expenseobserver.core.common.rippleClickable
import com.example.expenseobserver.core.data.CategoryColor
import com.example.expenseobserver.ui.theme.mainColor

@Composable
fun ColorsView(
    colorList: List<CategoryColor>,
    colorName: MutableState<String>,
    textResId: Int? = null
) {
    val resources = LocalContext.current.resources
    if (textResId != null) {
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = resources.getString(textResId),
            color = mainColor,
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp)
        )
        Spacer(modifier = Modifier.size(6.dp))
    }

    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(96.dp)
            .padding(4.dp)

    ) {
        items(colorList) { item ->
            Box(modifier = Modifier
                .padding(4.dp)
                .size(36.dp)
                .background(item.color, CircleShape)
                .aspectRatio(1f)
                .rippleClickable {
                    colorName.value = item.name
                })
        }
    }
}