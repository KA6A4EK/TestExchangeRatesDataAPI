package com.example.testexchangeratesdataapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SortSettingsDao {

    @Query("SELECT * FROM sort_settings WHERE id = 0 LIMIT 1")
    fun observe(): Flow<SortSettingsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: SortSettingsEntity)
}

