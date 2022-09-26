package com.example.familiwallet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.familiwallet.core.data.AppIcons
import com.example.familiwallet.core.data.IconActionType
import com.example.familiwallet.ui.theme.backgroundColor
import com.example.familiwallet.ui.theme.openColor12
import com.example.familiwallet.ui.theme.textColor

@Composable
fun CategoryIconGrid(item: Pair<IconActionType,List<AppIcons>>){
    val resources = LocalContext.current.resources
    
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
        .background(backgroundColor, RoundedCornerShape(20.dp))) {
        Text(
            text = resources.getString(item.first.titleRes),
            color = textColor,
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(8.dp))
        LazyHorizontalGrid(rows = GridCells.Adaptive(48.dp)){
            items(item.second){
                Icon(
                    painter = painterResource(id = AppIcons.getImageRes("").imageRes),
                    contentDescription = "",
                    tint = openColor12,
                    modifier = Modifier
                        .size(36.dp)
                        .aspectRatio(1f)
                )
            }
        }
    }
}