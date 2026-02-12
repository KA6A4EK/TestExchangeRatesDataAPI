package com.example.testexchangeratesdataapi.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testexchangeratesdataapi.domain.model.SortType
import com.example.testexchangeratesdataapi.domain.usecase.ApplySortingUseCase
import com.example.testexchangeratesdataapi.domain.usecase.GetSortSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class FilterUiState(
    val selectedSortType: SortType,
    val default : String
)

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val getSortSettingsUseCase: GetSortSettingsUseCase,
    private val applySortingUseCase: ApplySortingUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<FilterUiState?> = MutableStateFlow(null)
    val uiState: StateFlow<FilterUiState?> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val current = getSortSettingsUseCase().first()
            _uiState.value = FilterUiState(selectedSortType = current.sortType, default = current.default)
        }
    }

    fun onSortTypeSelected(sortType: SortType) {
        _uiState.value = _uiState.value?.copy(selectedSortType = sortType)
    }

    fun applySelection() {
        val sortType = _uiState.value?.selectedSortType ?: return
        viewModelScope.launch {
            applySortingUseCase(sortType,uiState.value?.default?:"USD")
        }
    }
}

