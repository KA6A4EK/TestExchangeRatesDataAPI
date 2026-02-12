package com.example.testexchangeratesdataapi.presentation.currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testexchangeratesdataapi.domain.model.CurrencyRate
import com.example.testexchangeratesdataapi.domain.model.SortType
import com.example.testexchangeratesdataapi.domain.usecase.ApplySortingUseCase
import com.example.testexchangeratesdataapi.domain.usecase.GetFavoritesUseCase
import com.example.testexchangeratesdataapi.domain.usecase.GetRatesUseCase
import com.example.testexchangeratesdataapi.domain.usecase.GetSortSettingsUseCase
import com.example.testexchangeratesdataapi.domain.usecase.SortRatesUseCase
import com.example.testexchangeratesdataapi.domain.usecase.ToggleFavoriteUseCase
import com.example.testexchangeratesdataapi.domain.usecase.UpdateLastRefreshTimeUseCase
import com.example.testexchangeratesdataapi.presentation.currencies.components.state.CurrenciesUiState
import com.example.testexchangeratesdataapi.presentation.currencies.components.state.CurrencyItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val getRatesUseCase: GetRatesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val sortRatesUseCase: SortRatesUseCase,
    private val applySortingUseCase: ApplySortingUseCase,
    private val getSortSettingsUseCase: GetSortSettingsUseCase,
    private val updateLastRefreshTimeUseCase: UpdateLastRefreshTimeUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<CurrenciesUiState> =
        MutableStateFlow(CurrenciesUiState.Loading)
    val uiState: StateFlow<CurrenciesUiState> = _uiState.asStateFlow()

    private var currentBaseCurrency: String = DEFAULT_BASE_CURRENCY
    private var currentSortType: SortType = SortType.ALPHABET_ASC
    private var currentRates: List<CurrencyRate> = emptyList()

    fun initViewModel(){
        viewModelScope.launch {
            _uiState.value = CurrenciesUiState.Loading
            val settings = getSortSettingsUseCase().first()
            currentSortType = settings.sortType
            currentBaseCurrency = settings.default
            loadRates()
            getSortSettingsUseCase()
                .onEach { newSortType ->
                    emitSorted()
                }
                .launchIn(viewModelScope)
        }
    }

    fun onBaseCurrencySelected(baseCurrency: String) {
        if (baseCurrency == currentBaseCurrency) return
        viewModelScope.launch {
            applySortingUseCase(default = baseCurrency)
        }
        currentBaseCurrency = baseCurrency
        loadRates()
    }




    fun onToggleFavorite(item: CurrencyItem) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(item.baseCurrency, item.code)
//                loadRates()
                setFavorites()
                emitSorted()
            } catch (e: Exception) {
                _uiState.value = CurrenciesUiState.Error(
                    message = e.message ?: "Не удалось обновить избранное"
                )
            }
        }
    }

    fun retry() {
        loadRates()
    }

    private fun loadRates() {
        _uiState.value = CurrenciesUiState.Loading
        viewModelScope.launch {
            try {
                val rates = getRatesUseCase(currentBaseCurrency)
                currentRates = rates

                setFavorites()

                emitSorted()
            } catch (e: Exception) {
                _uiState.value = CurrenciesUiState.Error(
                    message =  "Ошибка загрузки курсов\n${e.message}"
                )
            }
        }
    }

    private suspend fun setFavorites(){
        val favorites = getFavoritesUseCase().first()
        val favoriteSet = favorites
            .map { it.baseCurrency to it.targetCurrency }
            .toSet()

        val enriched = currentRates.map { rate ->
            rate.copy(
                isFavorite = favoriteSet.contains(rate.baseCurrency to rate.targetCurrency)
            )
        }
        currentRates = enriched
    }

    private fun emitSorted() {
        val sorted = sortRatesUseCase(currentRates, currentSortType)
        val items = sorted.map { rate ->
            CurrencyItem(
                code = rate.targetCurrency,
                quote = rate.rate,
                isFavorite = rate.isFavorite,
                baseCurrency = rate.baseCurrency
            )
        }
        _uiState.value = CurrenciesUiState.Success(
            baseCurrency = currentBaseCurrency,
            items = items,
            sortType = currentSortType,
            lastRefreshTime = updateLastRefreshTimeUseCase()
        )
    }

    companion object {
        private const val DEFAULT_BASE_CURRENCY = "EUR"
    }
}

