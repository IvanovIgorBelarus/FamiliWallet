package com.example.expenseobserver.core.di

import com.example.expenseobserver.features.transfer.domain.TransferUseCase
import com.example.expenseobserver.features.transfer.domain.TransferUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TransferModule {
    @Binds
    abstract fun provideBaseUseCase(
        transferUseCaseImpl: TransferUseCaseImpl
    ): TransferUseCase
}