package com.example.testexchangeratesdataapi.presentation.filter

import com.example.testexchangeratesdataapi.presentation.currencies.components.state.CurrencyItem

sealed interface FavoritesUiState {

    data object Loading : FavoritesUiState

    data class Success(
        val favorites: List<CurrencyItem>,
        val lastRefreshTime: String
    ) : FavoritesUiState

    data class Error(
        val message: String
    ) : FavoritesUiState
}