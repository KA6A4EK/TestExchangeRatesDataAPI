package com.example.testexchangeratesdataapi.domain.usecase

import com.example.testexchangeratesdataapi.domain.model.CurrencyRate
import com.example.testexchangeratesdataapi.domain.model.SortType
import javax.inject.Inject

class SortRatesUseCase @Inject constructor() {

    operator fun invoke(
        rates: List<CurrencyRate>,
        sortType: SortType
    ): List<CurrencyRate> {
        return when (sortType) {
            SortType.ALPHABET_ASC -> rates.sortedBy { it.targetCurrency }
            SortType.ALPHABET_DESC -> rates.sortedByDescending { it.targetCurrency }
            SortType.RATE_ASC -> rates.sortedBy { it.rate }
            SortType.RATE_DESC -> rates.sortedByDescending { it.rate }
        }
    }
}

