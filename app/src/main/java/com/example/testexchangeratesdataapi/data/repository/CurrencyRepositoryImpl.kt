package com.example.testexchangeratesdataapi.data.repository

import com.example.testexchangeratesdataapi.data.local.FavoriteCurrencyPairDao
import com.example.testexchangeratesdataapi.data.local.FavoriteCurrencyPairEntity
import com.example.testexchangeratesdataapi.data.remote.ExchangeRatesApiService
import com.example.testexchangeratesdataapi.domain.model.CurrencyRate
import com.example.testexchangeratesdataapi.domain.model.FavoriteCurrencyPair
import com.example.testexchangeratesdataapi.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: ExchangeRatesApiService,
    private val favoriteDao: FavoriteCurrencyPairDao
) : CurrencyRepository {

    override suspend fun getRates(baseCurrency: String): List<CurrencyRate> {
        val response = apiService.getLatestRates(baseCurrency)


        return response.rates.entries.map { (target, rate) ->
            CurrencyRate(
                baseCurrency = response.base,
                targetCurrency = target,
                rate = rate,
                isFavorite = false
            )
        }
    }

    override fun getFavoritePairs(): Flow<List<FavoriteCurrencyPair>> {
        return favoriteDao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun toggleFavoritePair(baseCurrency: String, targetCurrency: String) {
        val existing = favoriteDao.getByCurrencies(baseCurrency, targetCurrency)
        if (existing == null) {
            favoriteDao.insert(
                FavoriteCurrencyPairEntity(
                    baseCurrency = baseCurrency,
                    targetCurrency = targetCurrency
                )
            )
        } else {
            favoriteDao.deleteByCurrencies(baseCurrency, targetCurrency)
        }
    }

    private fun FavoriteCurrencyPairEntity.toDomain(): FavoriteCurrencyPair =
        FavoriteCurrencyPair(
            id = id,
            baseCurrency = baseCurrency,
            targetCurrency = targetCurrency
        )
}

