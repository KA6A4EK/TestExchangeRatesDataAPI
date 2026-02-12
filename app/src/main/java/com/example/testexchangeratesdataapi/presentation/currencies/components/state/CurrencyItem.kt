package com.example.testexchangeratesdataapi.presentation.currencies.components.state

data class CurrencyItem(
    val code: String,
    val quote: Double,
    val isFavorite: Boolean,
    val baseCurrency: String
) {
    val pair = "$baseCurrency/$code"
}