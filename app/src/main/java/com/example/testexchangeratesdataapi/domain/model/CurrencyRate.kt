package com.example.testexchangeratesdataapi.domain.model

data class CurrencyRate(
    val baseCurrency: String,
    val targetCurrency: String,
    val rate: Double,
    val isFavorite: Boolean
)

