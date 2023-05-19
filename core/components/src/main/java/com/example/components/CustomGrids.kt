package com.example.components

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common.rippleClickable
import com.example.data.CategoryColor
import com.example.data.Currency
import com.example.data.theme.backgroundColor
import com.example.data.theme.mainColor

@Preview(showBackground = true)
@Composable
fun ColorsView(
    colorList: List<CategoryColor> = CategoryColor.values().toList().dropLast(1),
    colorName: MutableState<String> = mutableStateOf(CategoryColor.COLOR0.name),
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
                .rippleClickable(backgroundColor) {
                    colorName.value = item.name
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrenciesView(
    currenciesList: List<Currency> = Currency.values().toList(),
    currencyName: MutableState<String> = mutableStateOf(Currency.BYN.name),
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
        rows = GridCells.Fixed(4),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(200.dp)
            .padding(4.dp)

    ) {
        items(currenciesList.dropLast(1)) { item ->
            Image(
                painter = painterResource(id = Currency.getCurrency(item.name).imageResId),
                modifier = Modifier
                    .size(48.dp)
                    .rippleClickable(backgroundColor) {
                        currencyName.value = item.name
                    },
                contentDescription = null
            )
        }
    }
}

