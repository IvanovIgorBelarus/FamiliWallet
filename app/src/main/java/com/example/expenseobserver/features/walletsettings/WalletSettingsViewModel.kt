package com.example.expenseobserver.features.walletsettings

import android.os.Build
import androidx.lifecycle.viewModelScope
import com.example.mylibrary.base.BaseUseCase
import com.example.mylibrary.base.BaseViewModel
import com.example.data.CategoryColor
import com.example.data.theme.UiState
import com.example.expenseobserver.features.walletsettings.data.WalletSettingsViewState
import com.example.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletSettingsViewModel @Inject constructor() : BaseViewModel<WalletSettingsViewState, BaseUseCase>() {

    override fun getData(forceLoad: Boolean) {
        val wallet = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Screen.WalletSettingsScreen.args?.getParcelable("wallet", com.example.data.UIModel.WalletModel::class.java)
        } else {
            Screen.WalletSettingsScreen.args?.getParcelable("wallet")
        }
        uiState.value = UiState.Success(WalletSettingsViewState(wallet))
    }

    fun onButtonClick(
        isNewWallet: Boolean,
        requestModel: com.example.data.UIModel.WalletModel,
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
        requestModel: com.example.data.UIModel.WalletModel,
        onSuccess: () -> Unit
    ) {
        addItem(requestModel
            .apply { uid = com.example.common.utils.UserUtils.getUsersUid() }) {
            getWallets(true)
            onSuccess.invoke()
        }
    }

    private fun updateWallet(
        requestModel: com.example.data.UIModel.WalletModel,
        onSuccess: () -> Unit = {}
    ) {
        updateItem(requestModel) {
            getWallets(true)
            onSuccess.invoke()
        }
    }

    private suspend fun getWallets(forceLoad: Boolean) = (getItems(com.example.common.WALLETS, forceLoad) as? List<com.example.data.UIModel.WalletModel>)

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