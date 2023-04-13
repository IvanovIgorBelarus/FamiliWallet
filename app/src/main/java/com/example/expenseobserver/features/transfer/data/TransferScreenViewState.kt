package com.example.expenseobserver.features.transfer.data

import com.example.expenseobserver.core.data.UIModel

class TransferScreenViewState(
    val walletFrom: UIModel.WalletModel,
    val walletsTo: List<UIModel.WalletModel>
)