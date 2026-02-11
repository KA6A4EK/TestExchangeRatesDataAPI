package com.example.testexchangeratesdataapi.di

import android.content.Context
import androidx.room.Room
import com.example.testexchangeratesdataapi.data.local.CurrencyDatabase
import com.example.testexchangeratesdataapi.data.local.FavoriteCurrencyPairDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "currency_tracker.db"

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CurrencyDatabase =
        Room.databaseBuilder(
            context,
            CurrencyDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideFavoriteCurrencyPairDao(
        database: CurrencyDatabase
    ): FavoriteCurrencyPairDao = database.favoriteCurrencyPairDao()

    @Provides
    @Singleton
    fun provideSortSettingsDao(
        database: CurrencyDatabase
    ): com.example.testexchangeratesdataapi.data.local.SortSettingsDao =
        database.sortSettingsDao()
}

