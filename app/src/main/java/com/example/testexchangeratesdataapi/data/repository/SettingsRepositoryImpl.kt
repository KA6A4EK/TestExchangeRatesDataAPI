package com.example.testexchangeratesdataapi.data.repository

import com.example.testexchangeratesdataapi.data.local.SettingsEntity
import com.example.testexchangeratesdataapi.data.local.datastore.DatastoreManager
import com.example.testexchangeratesdataapi.domain.model.SortType
import com.example.testexchangeratesdataapi.domain.repository.SettingsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl @Inject constructor(
    private val store: DatastoreManager

) : SettingsRepository {

    override suspend fun observeSortType(): Flow<SettingsEntity> {
        return store.read()
    }

    override suspend fun setSortType(sortType: SortType?, default: String?) {
        store.save(
            sortType, default
        )
    }

}

