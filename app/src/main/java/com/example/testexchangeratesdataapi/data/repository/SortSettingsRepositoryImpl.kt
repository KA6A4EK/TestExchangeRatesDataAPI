package com.example.testexchangeratesdataapi.data.repository

import com.example.testexchangeratesdataapi.data.local.SortSettingsDao
import com.example.testexchangeratesdataapi.data.local.SortSettingsEntity
import com.example.testexchangeratesdataapi.domain.model.SortType
import com.example.testexchangeratesdataapi.domain.repository.SortSettingsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SortSettingsRepositoryImpl @Inject constructor(
    private val dao: SortSettingsDao
) : SortSettingsRepository {

    override fun observeSortType(): Flow<SortType> {
        return dao.observe().map { entity ->
            val typeName = entity?.sortType
            if (typeName != null) {
                runCatching { SortType.valueOf(typeName) }.getOrDefault(DEFAULT_SORT_TYPE)
            } else {
                DEFAULT_SORT_TYPE
            }
        }
    }

    override suspend fun setSortType(sortType: SortType) {
        dao.insert(
            SortSettingsEntity(
                id = 0,
                sortType = sortType.name
            )
        )
    }

    companion object {
        private val DEFAULT_SORT_TYPE = SortType.ALPHABET_ASC
    }
}

