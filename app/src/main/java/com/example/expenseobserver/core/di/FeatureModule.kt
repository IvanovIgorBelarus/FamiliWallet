package com.example.expenseobserver.core.di

import com.alseda.auth_api.AuthApi
import com.alseda.auth_impl.navigation.AuthImpl
import com.alseda.enter_api.EnterApi
import com.alseda.enter_impl.navigation.EnterImpl
import com.alseda.splashscreen.navigation.SplashScreenApiImpl
import com.alseda.splashscreen_api.SplashScreenApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FeatureModule {

    @Binds
    abstract fun provideSplashFeatureApi(
        splashScreenApiImpl: SplashScreenApiImpl
    ): SplashScreenApi

    @Binds
    abstract fun provideAuthFeatureApi(
        authImpl: AuthImpl
    ): AuthApi

    @Binds
    abstract fun provideEnterFeatureApi(
        enterImpl: EnterImpl
    ): EnterApi
}