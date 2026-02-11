package com.example.testexchangeratesdataapi.domain.usecase

import com.example.testexchangeratesdataapi.domain.model.SortType
import com.example.testexchangeratesdataapi.domain.repository.SortSettingsRepository
import javax.inject.Inject

/**
 * UseCase для применения (сохранения) выбранного типа сортировки.
 * Фактическая сортировка списка выполняется через SortRatesUseCase.
 */
class ApplySortingUseCase @Inject constructor(
    private val sortSettingsRepository: SortSettingsRepository
) {

    suspend operator fun invoke(sortType: SortType) {
        sortSettingsRepository.setSortType(sortType)
    }
}

