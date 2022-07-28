package com.example.familiwallet.core.utils

import android.graphics.drawable.Icon
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessAlarm
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

sealed class AppIcons(val name: String, val icon: ImageVector){
//    object MedicalServices: AppIcons("MEDICAL_SERVICES",)
}

@Preview(showBackground = true)
@Composable
private fun showIcon(){
    Icon(imageVector = Icons.Outlined.AccessAlarm, contentDescription = "")
}
