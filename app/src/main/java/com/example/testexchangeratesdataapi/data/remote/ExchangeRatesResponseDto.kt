package com.example.testexchangeratesdataapi.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * DTO for /latest response from Exchange Rates Data API.
 *
 * Example:
 * {
 *   "success": true,
 *   "timestamp": 1519296206,
 *   "base": "EUR",
 *   "date": "2018-02-22",
 *   "rates": { "USD": 1.22975, ... }
 * }
 */
data class ExchangeRatesResponseDto(
    val base: String,
    val date: String,
    val rates: Map<String, Double>,
    val success: Boolean,
    val timestamp: Long
)

