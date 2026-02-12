package com.example.testexchangeratesdataapi.domain.usecase

import com.example.testexchangeratesdataapi.data.local.SettingsEntity
import com.example.testexchangeratesdataapi.domain.model.SortType
import com.example.testexchangeratesdataapi.domain.repository.SettingsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSortSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(): Flow<SettingsEntity> = repository.observeSortType()
}

