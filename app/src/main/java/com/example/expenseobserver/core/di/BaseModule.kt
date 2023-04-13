package com.example.expenseobserver.core.di

import com.example.expenseobserver.core.BaseUseCase
import com.example.expenseobserver.core.BaseUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BaseModule {
    @Binds
    abstract fun provideBaseUseCase(
        baseUseCaseImpl: BaseUseCaseImpl
    ): BaseUseCase
}