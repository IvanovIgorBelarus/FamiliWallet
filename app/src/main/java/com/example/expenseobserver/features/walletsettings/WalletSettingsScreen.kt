package com.example.expenseobserver.features.walletsettings

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.R
import com.example.expenseobserver.components.AmountTextField
import com.example.expenseobserver.components.MainButton
import com.example.expenseobserver.components.WalletTemplate
import com.example.expenseobserver.core.common.ShowScreen
import com.example.expenseobserver.core.common.rippleClickable
import com.example.expenseobserver.core.data.CategoryColor
import com.example.expenseobserver.core.data.Currency
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.features.walletsettings.data.WalletSettingsViewState
import com.example.expenseobserver.ui.theme.bottomBarUnselectedContentColor
import com.example.expenseobserver.ui.theme.mainColor

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
        UIModel.WalletModel(
            name = "Заначка",
            currency = Currency.BYN.name,
            value = 1000.20
        )
    ),
    walletSettingsViewModel: WalletSettingsViewModel? = null,
    navigation: NavHostController? = null
) {
    val walletName = remember { mutableStateOf(viewState.walletModel.name.orEmpty()) }
    val currency = remember { mutableStateOf(viewState.walletModel.currency ?: Currency.BYN.name) }
    val value = remember { mutableStateOf(viewState.walletModel.value?.toString().orEmpty()) }
    val backgroundColor = remember { mutableStateOf(viewState.walletModel.backgroundColor ?: CategoryColor.COLOR13.name) }
    val nameBackgroundColor = remember { mutableStateOf(viewState.walletModel.nameBackgroundColor ?: CategoryColor.COLOR10.name) }

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
                UIModel.WalletModel(
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
                onConfirmClick = {
                    walletSettingsViewModel?.createWallet(
                        UIModel.WalletModel(
                            name = walletName.value,
                            currency = currency.value,
                            value = value.value.toDouble(),
                            backgroundColor = backgroundColor.value,
                            nameBackgroundColor = nameBackgroundColor.value
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
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    val resources = LocalContext.current.resources

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        item {
            Spacer(modifier = Modifier.size(12.dp))
            AmountTextField(
                stringValue = value,
                placeHolderText = resources.getString(R.string.wallet_value_hit),
                modifier = Modifier.border(BorderStroke(1.dp, bottomBarUnselectedContentColor), RoundedCornerShape(10.dp)),
                showError = showError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                textBackgroundColor = Color.White
            )
        }
        item {
            ColorsView(
                colorName = backgroundColor,
                colorList = colorList,
                textResId = R.string.wallet_background
            )
        }
        item {
            ColorsView(
                colorName = nameBackgroundColor,
                colorList = colorList,
                textResId = R.string.wallet_name_background
            )
        }
        item {
            ButtonsLay(
                onCancelClick = { onCancelClick.invoke() },
                onConfirmClick = { onConfirmClick.invoke() }
            )
        }
    }
}

@Composable
private fun ColorsView(
    colorList: List<CategoryColor>,
    colorName: MutableState<String>,
    textResId: Int
) {
    val resources = LocalContext.current.resources

    Spacer(modifier = Modifier.size(6.dp))
    Text(
        text = resources.getString(textResId),
        color = mainColor,
        textAlign = TextAlign.Start,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
    Spacer(modifier = Modifier.size(6.dp))

    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(96.dp)
            .padding(4.dp)

    ) {
        items(colorList) { item ->
            Box(modifier = Modifier
                .padding(4.dp)
                .size(36.dp)
                .background(item.color, CircleShape)
                .aspectRatio(1f)
                .rippleClickable {
                    colorName.value = item.name
                })
        }
    }
    Spacer(modifier = Modifier.size(6.dp))
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun ButtonsLay(
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.Center) {
        MainButton(
            modifier = Modifier.weight(1f),
            text = R.string.cancel,
            isSelected = mutableStateOf(false)
        ) {
            onCancelClick.invoke()
        }
        Spacer(modifier = Modifier.size(24.dp))
        MainButton(
            modifier = Modifier.weight(1f),
            text = R.string.done,
            isSelected = mutableStateOf(true)
        ) {
            onConfirmClick.invoke()
        }
    }
}