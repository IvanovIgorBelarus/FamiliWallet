package com.example.familiwallet.features.transacrionscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.familiwallet.R
import com.example.familiwallet.components.AmountTextField
import com.example.familiwallet.components.CategoryRowList
import com.example.familiwallet.components.CategoryTabs
import com.example.familiwallet.components.TransactionButton
import com.example.familiwallet.core.common.CashType
import com.example.familiwallet.core.common.CategoryType
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.utils.UserUtils
import com.example.familiwallet.features.transacrionscreen.data.TransactionTabItem
import com.example.familiwallet.ui.theme.backgroundColor
import java.util.*

@Composable
fun TransactionDialog(
    data: List<UIModel.CategoryModel>,
    dismissDialog: () -> Unit,
    onButtonClick: (transactionModel: UIModel.TransactionModel) -> Unit
) {
    val resources = LocalContext.current.resources
    val currentState = remember { mutableStateOf(CategoryType.INCOME) }
    val selectedCategory = remember { mutableStateOf("") }
    val cashType = remember { mutableStateOf(CashType.CARDS) }
    val amount = remember { mutableStateOf("") }
    val showError = remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { dismissDialog.invoke() }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(backgroundColor, RoundedCornerShape(10.dp))
                .padding(8.dp, 16.dp)
        ) {
            CategoryTabs(tabList = tabList, currentState = currentState)
            Spacer(modifier = Modifier.size(24.dp))

            CategoryRowList(
                list = data,
                currentState,
                selectedCategory,
                showError
            )

            Row(horizontalArrangement = Arrangement.Center) {
                TransactionButton(
                    modifier = Modifier.weight(1f),
                    text = R.string.card,
                    isSelected = mutableStateOf(cashType.value == CashType.CARDS)
                ) { cashType.value = CashType.CARDS }
                Spacer(modifier = Modifier.size(8.dp))
                TransactionButton(
                    modifier = Modifier.weight(1f),
                    text = R.string.cash,
                    isSelected = mutableStateOf(cashType.value == CashType.CASHES)
                ) { cashType.value = CashType.CASHES }
            }
            Spacer(modifier = Modifier.size(24.dp))

            AmountTextField(amount = amount, showError)
            if (showError.value) {
                Text(
                    text = resources.getString(R.string.error_transaction_message),
                    color = Color.Red,
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.size(48.dp))

            TransactionButton(
                modifier = Modifier.fillMaxWidth(),
                text = R.string.confirm,
                isSelected = mutableStateOf(true)
            ) {
                if (amount.value.isNotEmpty() && selectedCategory.value.isNotEmpty()) {
                    onButtonClick.invoke(
                        UIModel.TransactionModel(
                            uid = UserUtils.getUsersUid(),
                            type = currentState.value.type,
                            category = selectedCategory.value,
                            currency = "BYN",
                            moneyType = cashType.value.type,
                            date = Calendar.getInstance().timeInMillis,
                            value = amount.value.toDouble()
                        )
                    )
                } else {
                    showError.value = true
                }
            }
        }
    }
}

private val tabList = listOf(
    TransactionTabItem.Income,
    TransactionTabItem.Expense,
    TransactionTabItem.Bank
)