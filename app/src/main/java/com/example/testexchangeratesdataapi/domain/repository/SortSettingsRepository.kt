package com.example.testexchangeratesdataapi.domain.repository

import com.example.testexchangeratesdataapi.domain.model.SortType
import kotlinx.coroutines.flow.Flow

interface SortSettingsRepository {

    fun observeSortType(): Flow<SortType>

    suspend fun setSortType(sortType: SortType)
}

