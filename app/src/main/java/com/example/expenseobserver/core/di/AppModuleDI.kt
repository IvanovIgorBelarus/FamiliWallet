package com.example.expenseobserver.core.di

import com.example.expenseobserver.core.BaseUseCase
import com.example.expenseobserver.core.BaseUseCaseImpl
import com.example.expenseobserver.core.repository.DataInteractor
import com.example.expenseobserver.core.repository.DataRepository
import com.example.expenseobserver.features.main.domain.usecase.PartnerUseCase
import com.example.expenseobserver.features.main.domain.usecase.PartnerUseCaseImpl
import com.example.expenseobserver.features.category.domain.usecase.CategoriesUseCase
import com.example.expenseobserver.features.category.domain.usecase.CategoriesUseCaseImpl
import com.example.expenseobserver.features.transacrionscreen.domain.TransactionUseCase
import com.example.expenseobserver.features.transacrionscreen.domain.TransactionUseCaseImpl
import com.example.expenseobserver.features.updateversion.domain.UpdateAppUseCase
import com.example.expenseobserver.features.updateversion.domain.UpdateAppUseCaseImpl
import com.example.expenseobserver.features.walletscreen.domain.usecase.WalletUseCase
import com.example.expenseobserver.features.walletscreen.domain.usecase.WalletUseCaseImpl
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
    abstract fun provideStartScreenInfoUseCase(
        mainScreenInfoUseCaseImpl: CategoriesUseCaseImpl
    ): CategoriesUseCase

    @Binds
    abstract fun providePartnerUseCase(
        partnerUseCaseImpl: PartnerUseCaseImpl
    ): PartnerUseCase

    @Binds
    abstract fun provideTransactionUseCase(
        transactionUseCase: TransactionUseCaseImpl
    ): TransactionUseCase

    @Binds
    abstract fun provideUpdateAppUseCase(
        updateAppUseCase: UpdateAppUseCaseImpl
    ): UpdateAppUseCase

    @Binds
    abstract fun provideWalletsUseCase(
        walletUseCaseImpl: WalletUseCaseImpl
    ): WalletUseCase

    @Binds
    abstract fun provideBaseUseCase(
        baseUseCaseImpl: BaseUseCaseImpl
    ): BaseUseCase
}
