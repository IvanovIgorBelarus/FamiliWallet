package com.example.expenseobserver.features.walletsettings

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.R
import com.example.components.AmountTextField
import com.example.components.ButtonsLay
import com.example.components.ColorsView
import com.example.components.CurrenciesView
import com.example.components.SwitchWithText
import com.example.components.WalletTemplate
import com.example.components.ShowScreen
import com.example.data.CategoryColor
import com.example.data.Currency
import com.example.expenseobserver.features.walletsettings.data.WalletSettingsViewState
import com.example.data.theme.bottomBarUnselectedContentColor

@Composable
fun WalletSettingsScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    walletSettingsViewModel: WalletSettingsViewModel = hiltViewModel()
) {

    ShowScreen(
        viewModel = walletSettingsViewModel,
        onSuccess = {
            UI(
                modifier = modifier,
                viewState = it as WalletSettingsViewState,
                walletSettingsViewModel = walletSettingsViewModel,
                navigation = navigation
            )
        }
    )

    LaunchedEffect(Unit) {
        walletSettingsViewModel.getData()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
private fun UI(
    modifier: Modifier = Modifier,
    viewState: WalletSettingsViewState = WalletSettingsViewState(
        com.example.data.UIModel.WalletModel(
            name = "Заначка",
            currency = Currency.BYN.name,
            value = 1000.20
        )
    ),
    walletSettingsViewModel: WalletSettingsViewModel? = null,
    navigation: NavHostController? = null
) {
    val isNewWallet = viewState.walletModel?.id == null
    val walletName = remember { mutableStateOf(viewState.walletModel?.name.orEmpty()) }
    val currency = remember { mutableStateOf(viewState.walletModel?.currency ?: Currency.BYN.name) }
    val value = remember { mutableStateOf(viewState.walletModel?.value?.toString().orEmpty()) }
    val backgroundColor = remember { mutableStateOf(viewState.walletModel?.backgroundColor ?: CategoryColor.COLOR13.name) }
    val nameBackgroundColor = remember { mutableStateOf(viewState.walletModel?.nameBackgroundColor ?: CategoryColor.COLOR10.name) }
    val isMainSource = remember { mutableStateOf(viewState.walletModel?.isMainSource ?: false) }

    val showError = remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.padding(horizontal = 8.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WalletTemplate(
                com.example.data.UIModel.WalletModel(
                    name = walletName.value,
                    currency = currency.value,
                    value = value.value.toDoubleOrNull(),
                    backgroundColor = backgroundColor.value,
                    nameBackgroundColor = nameBackgroundColor.value
                )
            )

            SettingsViews(
                walletName = walletName,
                currency = currency,
                value = value,
                backgroundColor = backgroundColor,
                nameBackgroundColor = nameBackgroundColor,
                showError = showError,
                colorList = walletSettingsViewModel?.getColors().orEmpty(),
                isNewWallet = isNewWallet,
                isMainSource = isMainSource,
                onConfirmClick = {
                    walletSettingsViewModel?.onButtonClick(
                        isNewWallet = isNewWallet,
                        requestModel = com.example.data.UIModel.WalletModel(
                            id = viewState.walletModel?.id,
                            uid = viewState.walletModel?.uid,
                            name = walletName.value,
                            currency = currency.value,
                            value = (value.value).toDoubleOrNull(),
                            backgroundColor = backgroundColor.value,
                            nameBackgroundColor = nameBackgroundColor.value,
                            isMainSource = isMainSource.value
                        )
                    ) {
                        navigation?.popBackStack()
                    }
                },
                onCancelClick = { navigation?.popBackStack() }
            )
        }
    }
}

@Composable
private fun SettingsViews(
    walletName: MutableState<String>,
    currency: MutableState<String>,
    value: MutableState<String>,
    backgroundColor: MutableState<String>,
    nameBackgroundColor: MutableState<String>,
    showError: MutableState<Boolean>,
    colorList: List<CategoryColor>,
    isNewWallet: Boolean,
    isMainSource: MutableState<Boolean>,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    val resources = LocalContext.current.resources

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            SwitchWithText(
                textResId = R.string.wallet_main_source,
                isChecked = isMainSource.value,
                onCheckedChange = { isMainSource.value = it }
            )
        }
        item {
            AmountTextField(
                stringValue = walletName,
                placeHolderText = resources.getString(R.string.wallet_name_hint),
                modifier = Modifier.border(BorderStroke(1.dp, bottomBarUnselectedContentColor), RoundedCornerShape(10.dp)),
                showError = showError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                textBackgroundColor = Color.White
            )
        }
        if (isNewWallet) {
            item {
                Spacer(modifier = Modifier.size(12.dp))
                AmountTextField(
                    stringValue = value,
                    placeHolderText = resources.getString(R.string.wallet_value_hint),
                    modifier = Modifier.border(BorderStroke(1.dp, bottomBarUnselectedContentColor), RoundedCornerShape(10.dp)),
                    showError = showError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textBackgroundColor = Color.White
                )
            }
        }
        item {
            ColorsView(
                colorName = backgroundColor,
                colorList = colorList,
                textResId = R.string.wallet_background
            )
            Spacer(modifier = Modifier.size(6.dp))
        }
        item {
            ColorsView(
                colorName = nameBackgroundColor,
                colorList = colorList,
                textResId = R.string.wallet_name_background
            )
            Spacer(modifier = Modifier.size(6.dp))
        }
        if (isNewWallet) {
            item {
                CurrenciesView(
                    currencyName = currency,
                    textResId = R.string.wallet_currency_title
                )
                Spacer(modifier = Modifier.size(6.dp))
            }
        }
        item {
            ButtonsLay(
                onCancelClick = { onCancelClick.invoke() },
                onConfirmClick = { onConfirmClick.invoke() }
            )
        }
    }
}