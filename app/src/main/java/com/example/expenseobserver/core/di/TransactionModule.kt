package com.example.expenseobserver.core.di

import com.example.expenseobserver.features.transacrionscreen.domain.TransactionUseCase
import com.example.expenseobserver.features.transacrionscreen.domain.TransactionUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TransactionModule {
    @Binds
    abstract fun provideTransactionUseCase(
        transactionUseCase: TransactionUseCaseImpl
    ): TransactionUseCase
}