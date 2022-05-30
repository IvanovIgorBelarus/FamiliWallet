package com.example.familiwallet.features.newcategory

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun NewTransactionScreen(modifier: Modifier = Modifier, text: String){
    Text(modifier = modifier.fillMaxSize(),
        text = text,
        fontSize = 40.sp
    )
}