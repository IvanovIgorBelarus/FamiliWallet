package com.example.expenseobserver.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.expenseobserver.R
import com.example.expenseobserver.core.common.rippleClickable
import com.example.expenseobserver.core.utils.UserUtils
import com.example.expenseobserver.navigation.Screen
import com.example.expenseobserver.ui.theme.backgroundColor
import com.example.expenseobserver.ui.theme.bottomBarBackgroundColor
import com.example.expenseobserver.ui.theme.textColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    navigation: NavHostController
) {
    val navBackStackEntry by navigation.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val titleText = Screen.getScreen(currentRoute).title.orEmpty()
    val showSettings = Screen.getScreen(currentRoute).route != Screen.TransactionScreen.route
    val painter = rememberImagePainter(data = UserUtils.getUserPhoto())
    TopAppBar(
        modifier = modifier
            .height(56.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = Color.White,
            containerColor = backgroundColor
        ),
        title = {MainTopBar(painter = painter, titleText = titleText)}
    )
//    {
//        if (showSettings) {
//            MainTopBar(painter = painter, titleText = titleText)
//        } else {
//            TransactionTopBar(titleText = titleText, navigation = navigation)
//        }
//    }
}

@Composable
private fun MainTopBar(
    painter: ImagePainter,
    titleText: String
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.size(24.dp))
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(
            text = titleText,
            color = textColor,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        //hide settings
//        Icon(
//            painter = painterResource(id = R.drawable.ic_settings_screen),
//            contentDescription = "",
//            modifier = Modifier.size(24.dp),
//            tint = Color.Black
//        )
//        Spacer(modifier = Modifier.size(12.dp))
    }
}

@Composable
private fun TransactionTopBar(
    titleText: String,
    navigation: NavHostController
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = titleText,
            color = textColor,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_cross),
            contentDescription = "",
            modifier = Modifier
                .size(24.dp)
                .rippleClickable { navigation.popBackStack() },
            tint = Color.Black
        )
        Spacer(modifier = Modifier.size(12.dp))
    }
}