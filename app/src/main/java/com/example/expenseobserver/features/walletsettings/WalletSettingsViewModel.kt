package com.example.expenseobserver.features.walletsettings

import android.os.Build
import androidx.lifecycle.viewModelScope
import com.example.expenseobserver.core.BaseUseCase
import com.example.expenseobserver.core.BaseViewModel
import com.example.expenseobserver.core.common.WALLETS
import com.example.expenseobserver.core.data.CategoryColor
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.data.UiState
import com.example.expenseobserver.core.utils.UserUtils
import com.example.expenseobserver.features.walletsettings.data.NewWalletModel
import com.example.expenseobserver.features.walletsettings.data.WalletSettingsViewState
import com.example.expenseobserver.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletSettingsViewModel @Inject constructor() : BaseViewModel<WalletSettingsViewState, BaseUseCase>() {

    override fun getData(forceLoad: Boolean) {
        val wallet = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Screen.WalletSettingsScreen.args?.getParcelable("wallet", UIModel.WalletModel::class.java)
        } else {
            Screen.WalletSettingsScreen.args?.getParcelable("wallet")
        }
        uiState.value = UiState.Success(WalletSettingsViewState(wallet))
    }

    fun onButtonClick(
        isNewWallet: Boolean,
        requestModel: UIModel.WalletModel,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val mainSourceWallet = getMainSource()
            if (requestModel.isMainSource && mainSourceWallet != null) {
                updateItem(mainSourceWallet.apply { isMainSource = false })
            }
            if (isNewWallet) {
                createWallet(requestModel, onSuccess)
            } else {
                updateWallet(requestModel, onSuccess)
            }
        }
    }

    private fun createWallet(
        requestModel: UIModel.WalletModel,
        onSuccess: () -> Unit
    ) {
        addItem(requestModel
            .apply { uid = UserUtils.getUsersUid() }) {
            getWallets(true)
            onSuccess.invoke()
        }
    }

    private fun updateWallet(
        requestModel: UIModel.WalletModel,
        onSuccess: () -> Unit = {}
    ) {
        updateItem(requestModel) {
            getWallets(true)
            onSuccess.invoke()
        }
    }

    private suspend fun getWallets(forceLoad: Boolean) = (getItems(WALLETS, forceLoad) as? List<UIModel.WalletModel>)

    private suspend fun getMainSource() = viewModelScope.async {
        try {
            return@async getWallets(false)?.firstOrNull { it.isMainSource }
        } catch (e: Exception) {
            return@async null
        }
    }.await()

    fun getColors() = listOf(
        CategoryColor.COLOR0,
        CategoryColor.COLOR1,
        CategoryColor.COLOR2,
        CategoryColor.COLOR3,
        CategoryColor.COLOR4,
        CategoryColor.COLOR5,
        CategoryColor.COLOR6,
        CategoryColor.COLOR7,
        CategoryColor.COLOR8,
        CategoryColor.COLOR9,
        CategoryColor.COLOR10,
        CategoryColor.COLOR11,
        CategoryColor.COLOR12,
        CategoryColor.COLOR13
    )
}