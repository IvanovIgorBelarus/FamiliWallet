package com.example.expenseobserver.features.walletsettings.data

import com.example.expenseobserver.core.data.UIModel

object NewWalletModel {
    private var model = UIModel.WalletModel()

    private var isNewWallet: Boolean = true

    fun getModel() = model

    fun isNewCategory() = isNewWallet

    fun setNewWalletModel(model: UIModel.WalletModel, isNewWallet: Boolean) {
        this.model = model
        this.isNewWallet = isNewWallet
    }

    fun clearModel() {
        model = UIModel.WalletModel()
    }
}