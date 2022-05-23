package com.example.familiwallet.core.di

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModuleDI {

    @Composable
    fun provideNavHostController() = rememberNavController()
}
