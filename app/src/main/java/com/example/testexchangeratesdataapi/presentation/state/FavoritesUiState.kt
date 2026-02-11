package com.example.testexchangeratesdataapi.presentation.state

import com.example.testexchangeratesdataapi.presentation.state.FavoritePair

sealed interface FavoritesUiState {

    data object Loading : FavoritesUiState

    data class Success(
        val favorites: List<FavoritePair>,
        val lastRefreshTime: String
    ) : FavoritesUiState

    data class Error(
        val message: String
    ) : FavoritesUiState
}

