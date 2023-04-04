package com.example.expenseobserver.core.di

import com.example.expenseobserver.features.category.domain.usecase.CategoriesUseCase
import com.example.expenseobserver.features.category.domain.usecase.CategoriesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryModule {
    @Binds
    abstract fun provideStartScreenInfoUseCase(
        categoriesUseCaseImpl: CategoriesUseCaseImpl
    ): CategoriesUseCase
}