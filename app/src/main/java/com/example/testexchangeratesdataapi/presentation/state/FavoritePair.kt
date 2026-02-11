package com.example.testexchangeratesdataapi.presentation.state

data class FavoritePair(
    val pairCode: String, // "EUR/AED"
    val quote: Double,
    val baseCurrency: String,
    val targetCurrency: String
)

