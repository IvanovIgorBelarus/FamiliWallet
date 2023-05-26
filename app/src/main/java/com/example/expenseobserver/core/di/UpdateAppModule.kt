package com.example.expenseobserver.core.di

import com.alseda.updateapp.domain.UpdateAppUseCase
import com.alseda.updateapp.domain.UpdateAppUseCaseImpl
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