package com.example.testexchangeratesdataapi.domain.usecase

import com.example.testexchangeratesdataapi.domain.repository.CurrencyRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(baseCurrency: String, targetCurrency: String) {
        repository.toggleFavoritePair(baseCurrency, targetCurrency)
    }
}

