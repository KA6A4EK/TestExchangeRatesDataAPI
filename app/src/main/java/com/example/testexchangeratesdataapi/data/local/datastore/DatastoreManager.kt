package com.example.testexchangeratesdataapi.data.local.datastore


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.testexchangeratesdataapi.data.local.SettingsEntity
import com.example.testexchangeratesdataapi.domain.model.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

val sortkey = stringPreferencesKey("sortType")
val defaultkey = stringPreferencesKey("defaultValue")

@Singleton
class DatastoreManager @Inject constructor(private val datastore: DataStore<Preferences>) {
    suspend fun save(sortType: SortType?, default: String?) {
        datastore.edit { preferences ->
           sortType?.let {   preferences[sortkey] = Json.encodeToString(sortType)}
            default?.let {   preferences[defaultkey] = Json.encodeToString(default)}
        }
    }

    suspend fun read(): Flow<SettingsEntity> = datastore.data.map { preferences ->
        SettingsEntity(
            preferences[sortkey]?.let { Json.decodeFromString<SortType>(it) }
                ?: SortType.ALPHABET_ASC,
            preferences[defaultkey]?.let { Json.decodeFromString<String>(it) } ?: "USD"
        )
    }

}

