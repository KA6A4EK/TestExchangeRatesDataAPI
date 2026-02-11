package com.example.testexchangeratesdataapi.domain.usecase

import com.example.testexchangeratesdataapi.domain.model.FavoriteCurrencyPair
import com.example.testexchangeratesdataapi.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke(): Flow<List<FavoriteCurrencyPair>> {
        return repository.getFavoritePairs()
    }
}

