package com.example.expenseobserver.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expenseobserver.core.common.rippleClickable
import com.example.expenseobserver.core.data.AppIcons
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.features.start_screen.data.StartScreenViewState
import com.example.expenseobserver.ui.theme.backgroundColor
import com.example.expenseobserver.ui.theme.mainColor
import com.example.expenseobserver.ui.theme.walletColor

@Preview(showBackground = true)
@Composable
fun WalletItems(
    viewState: StartScreenViewState = StartScreenViewState(emptyList(), emptyList(), emptyMap()),
    onSettingsClick: () -> Unit = {},
    onItemClick: (UIModel.WalletModel) -> Unit = {}
) {
    Column(
        modifier = Modifier.padding(start = 12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow(verticalAlignment = Alignment.CenterVertically) {
            item {
                WalletSettings(onClick = { onSettingsClick.invoke() })
            }
            if (viewState.walletList.isEmpty()) {
                item {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "<--- Откройте кошелёк", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    }
                }
            } else {
                items(items = viewState.walletList) {
                    WalletView(wallet = it) { wallet ->
                        onItemClick.invoke(wallet)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun WalletSettingsView(
    wallet: UIModel.WalletModel = UIModel.WalletModel(value = 10000000.00, currency = "USD", name = "Банк"),
    onClick: (UIModel.WalletModel) -> Unit = {},
    onSettingsClick: (UIModel.WalletModel) -> Unit = {},
    onDeleteClick: (UIModel.WalletModel) -> Unit = {},
) {
    val width = LocalConfiguration.current.screenWidthDp.dp - 64.dp
    val height = width * 0.6f
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.size(12.dp))
        WalletView(
            modifier = Modifier.size(width = width, height = height),
            wallet = wallet,
            onClick = onClick,
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            WalletSettings(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                iconId = AppIcons.BUILD.imageRes,
                iconSize = 24.dp,
                onClick = { onSettingsClick(wallet) },
            )

            WalletSettings(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                iconId = AppIcons.DELETE.imageRes,
                iconSize = 24.dp,
                onClick = { onDeleteClick(wallet) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WalletView(
    modifier: Modifier = Modifier
        .size(width = 202.dp, height = 120.dp)
        .padding(2.dp),
    wallet: UIModel.WalletModel = UIModel.WalletModel(value = 10000000.00, currency = "USD", name = "Банк"),
    onClick: (UIModel.WalletModel) -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(color = Color.Black, RoundedCornerShape(8.dp))
            .rippleClickable { onClick.invoke(wallet) }
    ) {
        Column(
            modifier = Modifier.padding(start = 12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Баланс",
                color = Color.White,
                fontSize = 10.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = wallet.value.toString() + " " + wallet.currency,
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = wallet.name.orEmpty(),
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .background(color = walletColor, RoundedCornerShape(4.dp))
                    .padding(2.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WalletSettings(
    modifier: Modifier = Modifier
        .size(96.dp)
        .padding(16.dp),
    iconId: Int = AppIcons.SETTINGS.imageRes,
    iconSize: Dp = 48.dp,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(10.dp))
            .rippleClickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "",
            tint = mainColor,
            modifier = Modifier.size(iconSize)
        )
    }
}