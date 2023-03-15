package com.example.expenseobserver.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expenseobserver.R
import com.example.expenseobserver.core.common.rippleClickable
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
                WalletSettings()
            }
            items(items = viewState.walletList) {
                WalletView(it) { wallet ->
                    onItemClick.invoke(wallet)
                }
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun WalletView(
    wallet: UIModel.WalletModel = UIModel.WalletModel(value = 10000000.00, currency = "USD", name = "Банк"),
    onClick: (UIModel.WalletModel) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(width = 202.dp, height = 120.dp)
            .padding(2.dp)
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
private fun WalletSettings(
    onClick: () -> Unit = {}
){
    Box(
        modifier = Modifier
            .size(96.dp)
            .padding(16.dp)
            .background(backgroundColor, RoundedCornerShape(10.dp))
            .rippleClickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_account_balance_wallet),
            contentDescription = "",
            tint = mainColor,
            modifier = Modifier.size(48.dp)
        )
    }
}