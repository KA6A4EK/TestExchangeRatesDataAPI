package com.example.testexchangeratesdataapi.di

import com.example.testexchangeratesdataapi.domain.repository.CurrencyRepository
import com.example.testexchangeratesdataapi.domain.usecase.GetFavoritesUseCase
import com.example.testexchangeratesdataapi.domain.usecase.GetRatesUseCase
import com.example.testexchangeratesdataapi.domain.usecase.SortRatesUseCase
import com.example.testexchangeratesdataapi.domain.usecase.ToggleFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetRatesUseCase(
        repository: CurrencyRepository
    ): GetRatesUseCase = GetRatesUseCase(repository)

    @Provides
    @Singleton
    fun provideToggleFavoriteUseCase(
        repository: CurrencyRepository
    ): ToggleFavoriteUseCase = ToggleFavoriteUseCase(repository)

    @Provides
    @Singleton
    fun provideGetFavoritesUseCase(
        repository: CurrencyRepository
    ): GetFavoritesUseCase = GetFavoritesUseCase(repository)

    @Provides
    @Singleton
    fun provideSortRatesUseCase(): SortRatesUseCase = SortRatesUseCase()
}

