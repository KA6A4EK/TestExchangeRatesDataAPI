package com.example.testexchangeratesdataapi.domain.usecase

import com.example.testexchangeratesdataapi.domain.model.SortType
import com.example.testexchangeratesdataapi.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * UseCase для применения (сохранения) выбранного типа сортировки.
 * Фактическая сортировка списка выполняется через SortRatesUseCase.
 */
class ApplySortingUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(sortType: SortType? = null,default : String?= null) {
        settingsRepository.setSortType(sortType, default)
    }
}

