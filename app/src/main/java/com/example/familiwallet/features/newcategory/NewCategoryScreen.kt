package com.example.familiwallet.features.newcategory

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun NewCategoryScreen(modifier: Modifier = Modifier){
    Text(modifier = modifier.fillMaxSize(),
        text = "Новая категория",
        fontSize = 40.sp
    )
}