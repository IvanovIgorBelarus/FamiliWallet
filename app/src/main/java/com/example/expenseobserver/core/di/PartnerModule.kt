package com.example.expenseobserver.core.di

import com.example.expenseobserver.features.main.domain.usecase.PartnerUseCase
import com.example.expenseobserver.features.main.domain.usecase.PartnerUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PartnerModule {
    @Binds
    abstract fun providePartnerUseCase(
        partnerUseCaseImpl: PartnerUseCaseImpl
    ): PartnerUseCase
}