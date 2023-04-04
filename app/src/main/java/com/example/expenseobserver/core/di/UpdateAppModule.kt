package com.example.expenseobserver.core.di

import com.example.expenseobserver.features.updateversion.domain.UpdateAppUseCase
import com.example.expenseobserver.features.updateversion.domain.UpdateAppUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UpdateAppModule {
    @Binds
    abstract fun provideUpdateAppUseCase(
        updateAppUseCase: UpdateAppUseCaseImpl
    ): UpdateAppUseCase
}