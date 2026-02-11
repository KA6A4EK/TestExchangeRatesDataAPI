package com.example.testexchangeratesdataapi.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Exchange Rates Data API (apilayer) service.
 *
 * Base URL: https://api.apilayer.com/exchangerates_data/
 * Endpoint: /latest?base={BASE}
 */
interface ExchangeRatesApiService {

    @GET("latest")
    suspend fun getLatestRates(
        @Query("base") baseCurrency: String
    ):  ExchangeRatesResponseDto
}

