package com.example.testexchangeratesdataapi.presentation.state

import com.example.testexchangeratesdataapi.domain.model.SortType
import com.example.testexchangeratesdataapi.presentation.state.CurrencyItem

sealed interface CurrenciesUiState {

    data object Loading : CurrenciesUiState

    data class Success(
        val baseCurrency: String,
        val items: List<CurrencyItem>,
        val sortType: SortType,
        val lastRefreshTime: String
    ) : CurrenciesUiState

    data class Error(
        val message: String
    ) : CurrenciesUiState
}

