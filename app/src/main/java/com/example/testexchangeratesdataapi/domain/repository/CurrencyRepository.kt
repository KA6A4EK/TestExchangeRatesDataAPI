package com.example.testexchangeratesdataapi.domain.repository

import com.example.testexchangeratesdataapi.domain.model.CurrencyRate
import com.example.testexchangeratesdataapi.domain.model.FavoriteCurrencyPair
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    /**
     * Load latest rates for the given base currency from remote API.
     */
    suspend fun getRates(baseCurrency: String): List<CurrencyRate>

    /**
     * Stream of all favorite currency pairs.
     */
    fun getFavoritePairs(): Flow<List<FavoriteCurrencyPair>>

    /**
     * Toggle favorite status of a pair.
     */
    suspend fun toggleFavoritePair(baseCurrency: String, targetCurrency: String)
}

