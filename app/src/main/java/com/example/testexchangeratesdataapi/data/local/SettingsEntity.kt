package com.example.testexchangeratesdataapi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testexchangeratesdataapi.domain.model.SortType
import kotlinx.serialization.Serializable

@Serializable
data class SettingsEntity(
    val sortType: SortType = SortType.ALPHABET_ASC,
    val default : String = "USD",
)


