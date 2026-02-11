package com.example.testexchangeratesdataapi.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testexchangeratesdataapi.domain.model.SortType
import com.example.testexchangeratesdataapi.presentation.viewmodel.FilterViewModel

@Composable
fun FilterScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: FilterViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header with Back Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Filters",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Sort By Section
        Text(
            text = "SORT BY",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Sort Options
        val sortOptions = listOf(
            SortType.ALPHABET_ASC to "Code A-Z",
            SortType.ALPHABET_DESC to "Code Z-A",
            SortType.RATE_ASC to "Quote Asc.",
            SortType.RATE_DESC to "Quote Desc."
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            sortOptions.forEach { (sortType, label) ->
                val isSelected = uiState?.selectedSortType == sortType
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onSortTypeSelected(sortType)
                        }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = {
                            viewModel.onSortTypeSelected(sortType)
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = label,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Apply Button
        Button(
            onClick = {
                viewModel.applySelection()
                onNavigateBack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Apply",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
