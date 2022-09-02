package com.alitafreshi.ayannewnetworking.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object KtorClientModule {

    @Singleton
    @Provides
    fun provideKtorClient() {

    }
}