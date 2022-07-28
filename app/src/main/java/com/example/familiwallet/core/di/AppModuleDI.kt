package com.example.familiwallet.core.di

import com.example.familiwallet.core.repository.DataInteractor
import com.example.familiwallet.core.repository.DataRepository
import com.example.familiwallet.features.start_screen.domain.usecase.StartScreenInfoUseCase
import com.example.familiwallet.features.start_screen.domain.usecase.StartScreenInfoUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun provideDataInteractor(
        dataInteractor: DataInteractor
    ): DataRepository

    @Binds
    abstract fun provideMainScreenInfoUseCase(
        mainScreenInfoUseCaseImpl: StartScreenInfoUseCaseImpl
    ): StartScreenInfoUseCase
}
