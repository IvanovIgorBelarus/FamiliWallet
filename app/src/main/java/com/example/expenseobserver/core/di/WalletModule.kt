package com.example.expenseobserver.core.di

import com.example.expenseobserver.features.walletscreen.domain.usecase.WalletUseCase
import com.example.expenseobserver.features.walletscreen.domain.usecase.WalletUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WalletModule {
    @Binds
    abstract fun provideWalletsUseCase(
        walletUseCaseImpl: WalletUseCaseImpl
    ): WalletUseCase
}