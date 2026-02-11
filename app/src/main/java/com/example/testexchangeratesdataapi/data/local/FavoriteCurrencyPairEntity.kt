package com.example.testexchangeratesdataapi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity representing a favorite currency pair.
 *
 * id is auto-generated, uniqueness is effectively defined by (baseCurrency, targetCurrency).
 */
@Entity(tableName = "favorite_currency_pairs")
data class FavoriteCurrencyPairEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val baseCurrency: String,
    val targetCurrency: String
)

