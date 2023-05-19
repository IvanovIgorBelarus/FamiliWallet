package com.example.expenseobserver.features.transacrionscreen

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.expenseobserver.R
import com.example.components.AmountTextField
import com.example.components.CategoryRowList
import com.example.components.CustomDatePickerDialog
import com.example.components.MainButton
import com.example.components.ThreeTabsLay
import com.example.components.WalletItems
import com.example.components.rememberFragmentManager
import com.example.common.rippleClickable
import com.example.data.Currency
import com.example.common.utils.toStringDateFormatWithToday
import com.example.data.TransactionTabItem
import com.example.data.theme.backgroundColor
import com.example.data.theme.buttonColor
import com.example.data.theme.mainColor
import com.example.data.theme.textColor
import java.util.*

@SuppressLint("UnrememberedMutableState")
@Composable
fun TransactionDialog(
    categoryData: List<com.example.data.UIModel.CategoryModel>,
    walletData: List<com.example.data.UIModel.WalletModel>,
    dismissDialog: () -> Unit,
    onButtonClick: (transactionModel: com.example.data.UIModel.TransactionModel) -> Unit
) {
    val resources = LocalContext.current.resources

    val dialogWidth = LocalConfiguration.current.screenWidthDp.dp * 9 / 10

    val currentState = remember { mutableStateOf(0) }
    val selectedCategory = remember { mutableStateOf("") }
    val selectedWallet = remember { mutableStateOf(walletData.getOrNull(0)) }
    val amount = remember { mutableStateOf("") }
    val showError = remember { mutableStateOf(false) }
    val operationDate = remember { mutableStateOf(Date().time) }
    val showDatePickerDialog = remember { mutableStateOf(false) }

    val datePicker = CustomDatePickerDialog(
        selectedDate = operationDate.value,
        dismiss = { showDatePickerDialog.value = false },
        onDateSelected = { selectedDate ->
            operationDate.value = selectedDate
            showDatePickerDialog.value = false
        }
    )

    if (showDatePickerDialog.value) {
        datePicker.show(rememberFragmentManager(), "DatePicker")
    }

    Dialog(
        onDismissRequest = { dismissDialog.invoke() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(dialogWidth)
                .background(backgroundColor, RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {
            DateView(showDatePickerDialog = showDatePickerDialog, operationDate = operationDate)

            WalletItems(
                walletsList = walletData,
                selectedWallet = selectedWallet,
                walletModifier = Modifier
                    .size(width = 160.dp, height = 96.dp)
                    .padding(2.dp)
            ) { wallet ->
                selectedWallet.value = wallet
            }

            ThreeTabsLay(tabList = tabList, currentState = currentState)

            CategoryRowList(
                list = categoryData,
                currentState,
                selectedCategory,
                showError
            )

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

            MainButton(
                modifier = Modifier.fillMaxWidth(),
                text = R.string.confirm,
                isSelected = mutableStateOf(true)
            ) {
                if (amount.value.isNotEmpty() && selectedCategory.value.isNotEmpty()) {
                    onButtonClick.invoke(
                        com.example.data.UIModel.TransactionModel(
                            uid = com.example.common.utils.UserUtils.getUsersUid(),
                            type = com.example.common.CategoryType.getCategory(currentState.value).type,
                            category = selectedCategory.value,
                            currency = selectedWallet.value?.currency ?: Currency.BYN.name,
                            moneyType = selectedWallet.value?.id,
                            date = operationDate.value,
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

@Composable
private fun DateView(
    showDatePickerDialog: MutableState<Boolean>,
    operationDate: MutableState<Long>
) {
    Row(
        modifier = Modifier
            .rippleClickable(backgroundColor) { showDatePickerDialog.value = true }
            .padding(4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = operationDate.value.toStringDateFormatWithToday,
            fontSize = 18.sp,
            color = textColor,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_time_range),
            contentDescription = "",
            tint = mainColor,
            modifier = Modifier.size(28.dp)
        )
    }
    Spacer(modifier = Modifier.size(12.dp))
}

private val tabList = listOf(
    TransactionTabItem.Expense,
    TransactionTabItem.Income
//    TransactionTabItem.Bank
)