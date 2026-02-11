package com.example.testexchangeratesdataapi.domain.usecase

import com.example.testexchangeratesdataapi.domain.model.SortType
import com.example.testexchangeratesdataapi.domain.repository.SortSettingsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSortSettingsUseCase @Inject constructor(
    private val repository: SortSettingsRepository
) {

    operator fun invoke(): Flow<SortType> = repository.observeSortType()
}

