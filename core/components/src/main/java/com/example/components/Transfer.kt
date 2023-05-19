package com.example.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common.formatAmount
import com.example.common.longRippleClickable
import com.example.common.utils.toStringFormat
import com.example.data.UIModel
import com.example.data.theme.backgroundColor
import com.example.data.theme.expensesColor
import com.example.data.theme.textColor
import com.example.data.theme.textColorGrey
import com.example.data.theme.walletColor

@Composable
fun TransferView(
    walletModel: UIModel.WalletModel,
    transfer: UIModel.TransferModel,
    onClick: (UIModel.TransferModel) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(8.dp, 2.dp, 8.dp, 2.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(0.dp, 4.dp)
            .fillMaxWidth()
            .requiredHeight(80.dp)
            .longRippleClickable(color = backgroundColor, onLongClick = { onClick.invoke(transfer) }),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconColor = if (walletModel.id == transfer.targetId) walletColor else expensesColor
        val iconRotation = if (walletModel.id == transfer.targetId) 0f else 180f
        val amountText = "${transfer.value.formatAmount()} ${walletModel.currency ?: "BYN"}"
        val transferText = if (walletModel.id == transfer.targetId) "Пополнение" else "Перевод"

        Spacer(modifier = Modifier.size(16.dp))
        Icon(
            painter = painterResource(id = com.example.data.R.drawable.ic_baseline_arrow_forward_24),
            contentDescription = "",
            tint = iconColor,
            modifier = Modifier
                .size(36.dp)
                .rotate(iconRotation)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column() {
            Text(
                text = transferText,
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            if (transfer.date != null) {
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = transfer.date?.toStringFormat.orEmpty(),
                    color = textColorGrey,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = amountText,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun TransferViewPreview() {
    TransferView(
        walletModel = UIModel.WalletModel(id = "1", currency = "BYN"),
        transfer = UIModel.TransferModel(sourceId = "1", date = 1647464400000, value = 12.00)
    )
}