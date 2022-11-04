package com.example.expenseobserver.features.transacrionscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.expenseobserver.R
import com.example.expenseobserver.components.AmountTextField
import com.example.expenseobserver.components.CategoryRowList
import com.example.expenseobserver.components.ThreeTabsLay
import com.example.expenseobserver.components.TransactionButton
import com.example.expenseobserver.core.common.CashType
import com.example.expenseobserver.core.common.CategoryType
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.utils.UserUtils
import com.example.expenseobserver.features.transacrionscreen.data.TransactionTabItem
import com.example.expenseobserver.ui.theme.backgroundColor
import com.example.expenseobserver.ui.theme.buttonColor
import java.util.*

@Composable
fun TransactionDialog(
    data: List<UIModel.CategoryModel>,
    dismissDialog: () -> Unit,
    onButtonClick: (transactionModel: UIModel.TransactionModel) -> Unit
) {
    val resources = LocalContext.current.resources
    val currentState = remember { mutableStateOf(0) }
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
            ThreeTabsLay(tabList = tabList, currentState = currentState)
            Spacer(modifier = Modifier.size(24.dp))

            CategoryRowList(
                list = data,
                currentState,
                selectedCategory,
                showError
            )
            Spacer(modifier = Modifier.size(4.dp))
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

            AmountTextField(
                stringValue = amount,
                placeHolderText = resources.getString(R.string.amount),
                modifier = Modifier.border(BorderStroke(1.dp, buttonColor), RoundedCornerShape(10.dp)),
                showError = showError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
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
                            type = CategoryType.getCategory(currentState.value).type,
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