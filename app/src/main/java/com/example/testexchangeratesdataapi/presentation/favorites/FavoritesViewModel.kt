package com.example.testexchangeratesdataapi.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testexchangeratesdataapi.domain.usecase.FormatCurrencyPairUseCase
import com.example.testexchangeratesdataapi.domain.usecase.GetFavoritesUseCase
import com.example.testexchangeratesdataapi.domain.usecase.GetRatesUseCase
import com.example.testexchangeratesdataapi.domain.usecase.ToggleFavoriteUseCase
import com.example.testexchangeratesdataapi.domain.usecase.UpdateLastRefreshTimeUseCase
import com.example.testexchangeratesdataapi.presentation.currencies.components.state.CurrencyItem
import com.example.testexchangeratesdataapi.presentation.filter.FavoritesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.iterator

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val getRatesUseCase: GetRatesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val formatCurrencyPairUseCase: FormatCurrencyPairUseCase,
    private val updateLastRefreshTimeUseCase: UpdateLastRefreshTimeUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<FavoritesUiState> =
        MutableStateFlow(FavoritesUiState.Loading)
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase()
                .catch { e ->
                    _uiState.value = FavoritesUiState.Error(
                        message = e.message ?: "Ошибка загрузки избранных пар"
                    )
                }
                .collectLatest { favorites ->
                    try {
                        // сгруппировать избранные по базовой валюте и получить актуальные курсы
                        val groupedByBase = favorites.groupBy { it.baseCurrency }
                        val pairs = mutableListOf<CurrencyItem>()

                        for ((base, list) in groupedByBase) {
                            val rates = getRatesUseCase(base)
                            val rateMap = rates.associateBy { it.targetCurrency }

                            for (fav in list) {
                                val rate = rateMap[fav.targetCurrency]
                                val quote = rate?.rate ?: 0.0
                                val pairCode = formatCurrencyPairUseCase(
                                    fav.baseCurrency,
                                    fav.targetCurrency
                                )
                                pairs.add(
                                    CurrencyItem(
                                        quote = quote,
                                        baseCurrency = fav.baseCurrency,
                                        code = fav.targetCurrency,
                                        isFavorite = true
                                    )
                                )
                            }
                        }

                        _uiState.value = FavoritesUiState.Success(
                            favorites = pairs,
                            lastRefreshTime = updateLastRefreshTimeUseCase()
                        )
                    } catch (e: Exception) {
                        _uiState.value = FavoritesUiState.Error(
                            message = e.message ?: "Ошибка загрузки курсов для избранных"
                        )
                    }
                }
        }
    }

    fun onToggleFavorite(baseCurrency: String, targetCurrency: String) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(baseCurrency, targetCurrency)
            } catch (e: Exception) {
                _uiState.value = FavoritesUiState.Error(
                    message = e.message ?: "Не удалось обновить избранное"
                )
            }
        }
    }
}