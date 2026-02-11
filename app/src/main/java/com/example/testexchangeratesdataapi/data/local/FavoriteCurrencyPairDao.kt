package com.example.testexchangeratesdataapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCurrencyPairDao {

    @Query("SELECT * FROM favorite_currency_pairs")
    fun getAll(): Flow<List<FavoriteCurrencyPairEntity>>

    @Query(
        "SELECT * FROM favorite_currency_pairs " +
            "WHERE baseCurrency = :base AND targetCurrency = :target LIMIT 1"
    )
    suspend fun getByCurrencies(
        base: String,
        target: String
    ): FavoriteCurrencyPairEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pair: FavoriteCurrencyPairEntity)

    @Query("DELETE FROM favorite_currency_pairs WHERE baseCurrency = :base AND targetCurrency = :target")
    suspend fun deleteByCurrencies(
        base: String,
        target: String
    )
}

