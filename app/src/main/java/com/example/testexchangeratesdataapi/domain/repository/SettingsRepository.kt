package com.example.testexchangeratesdataapi.domain.repository

import com.example.testexchangeratesdataapi.data.local.SettingsEntity
import com.example.testexchangeratesdataapi.domain.model.SortType
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun observeSortType(): Flow<SettingsEntity>

    suspend fun setSortType(sortType: SortType?,default: String?)
}

