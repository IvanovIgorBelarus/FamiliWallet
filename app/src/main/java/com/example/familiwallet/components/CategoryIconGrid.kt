package com.example.familiwallet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.familiwallet.core.common.noRippleClickable
import com.example.familiwallet.core.common.rippleClickable
import com.example.familiwallet.core.data.AppIcons
import com.example.familiwallet.core.data.IconActionType
import com.example.familiwallet.ui.theme.backgroundColor
import com.example.familiwallet.ui.theme.mainColor
import com.example.familiwallet.ui.theme.openColor12

@Composable
fun CategoryIconGrid(
    item: Pair<IconActionType, List<AppIcons>>,
    icon: MutableState<AppIcons>
) {
    val resources = LocalContext.current.resources
    val iconsItems = item.second
    val rowCount = if (iconsItems.size < 6) 1 else iconsItems.size / 6
    var startPos = 0
    var endPos = if (iconsItems.size < 6) iconsItems.size else 6

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(backgroundColor, RoundedCornerShape(20.dp))
    ) {
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = resources.getString(item.first.titleRes),
            color = mainColor,
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp)
        )

        for (i in 0..rowCount) {
            DrawIconsRow(sublist = iconsItems.subList(startPos, endPos), icon)
            startPos = endPos
            endPos += 6
            if (endPos > iconsItems.size) {
                endPos = iconsItems.size
            }
        }
    }
}

@Composable
private fun DrawIconsRow(
    sublist: List<AppIcons>,
    icon: MutableState<AppIcons>
) {
    if (sublist.isNotEmpty()) {
        val items = mutableListOf<AppIcons>()
        items.addAll(sublist)
        if (items.size < 6) {
            for (i in items.size..5) {
                items.add(AppIcons.UNKNOWN)
            }
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            items.forEach { iconItem ->
                val modifier = if (iconItem != AppIcons.UNKNOWN) {
                    Modifier.rippleClickable(color = Color.White) {
                        if (iconItem != AppIcons.UNKNOWN) {
                            icon.value = iconItem
                        }
                    }
                } else {
                    Modifier
                }
                Icon(
                    painter = painterResource(id = AppIcons.getImageRes(iconItem.name).imageRes),
                    contentDescription = "",
                    tint = if (iconItem == AppIcons.UNKNOWN) backgroundColor else openColor12,
                    modifier = modifier
                        .size(36.dp)
                        .weight(1f)
                )
                Spacer(modifier = Modifier.size(12.dp))
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
    }
}