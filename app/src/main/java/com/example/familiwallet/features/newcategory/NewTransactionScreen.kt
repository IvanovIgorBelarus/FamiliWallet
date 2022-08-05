package com.example.familiwallet.features.newcategory

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun NewTransactionScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    text: String
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = modifier.fillMaxSize(),
            text = text,
            fontSize = 40.sp
        )
    }
}