package com.example.expenseobserver.features.walletsettings.data

import com.example.expenseobserver.core.data.UIModel

object NewWalletModel {
    private var model = UIModel.WalletModel()

    fun getModel() = model

    fun setNewWalletModel(model: UIModel.WalletModel) {
        this.model = model
    }

    fun clearModel() {
        model = UIModel.WalletModel()
    }
}