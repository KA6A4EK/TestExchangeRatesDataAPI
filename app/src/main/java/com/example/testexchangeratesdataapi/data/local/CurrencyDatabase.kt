package com.example.testexchangeratesdataapi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        FavoriteCurrencyPairEntity::class,
        SortSettingsEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun favoriteCurrencyPairDao(): FavoriteCurrencyPairDao
    abstract fun sortSettingsDao(): SortSettingsDao
}

