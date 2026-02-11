package com.example.testexchangeratesdataapi.di

import com.example.testexchangeratesdataapi.data.repository.CurrencyRepositoryImpl
import com.example.testexchangeratesdataapi.data.repository.SortSettingsRepositoryImpl
import com.example.testexchangeratesdataapi.domain.repository.CurrencyRepository
import com.example.testexchangeratesdataapi.domain.repository.SortSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCurrencyRepository(
        impl: CurrencyRepositoryImpl
    ): CurrencyRepository

    @Binds
    @Singleton
    abstract fun bindSortSettingsRepository(
        impl: SortSettingsRepositoryImpl
    ): SortSettingsRepository
}

