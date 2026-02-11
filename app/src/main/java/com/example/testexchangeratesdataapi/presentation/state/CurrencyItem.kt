package com.example.testexchangeratesdataapi.presentation.state

data class CurrencyItem(
    val code: String,
    val quote: Double,
    val isFavorite: Boolean,
    val baseCurrency: String
)

