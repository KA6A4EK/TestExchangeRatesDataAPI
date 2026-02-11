package com.example.testexchangeratesdataapi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sort_settings")
data class SortSettingsEntity(
    @PrimaryKey val id: Int = 0,
    val sortType: String
)

