package com.example.testexchangeratesdataapi.domain.usecase

import javax.inject.Inject

class FormatCurrencyPairUseCase @Inject constructor() {

    operator fun invoke(baseCurrency: String, targetCurrency: String): String {
        return "$baseCurrency/$targetCurrency"
    }
}

