package com.example.testexchangeratesdataapi.domain.usecase

import com.example.testexchangeratesdataapi.domain.model.CurrencyRate
import com.example.testexchangeratesdataapi.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(baseCurrency: String): List<CurrencyRate> {
        return repository.getRates(baseCurrency)
    }
}

