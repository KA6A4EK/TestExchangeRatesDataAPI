package com.example.testexchangeratesdataapi.presentation.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testexchangeratesdataapi.presentation.common.TopAppBar
import com.example.testexchangeratesdataapi.domain.model.SortType
import com.example.testexchangeratesdataapi.ui.theme.BlueMain

@Composable
fun FilterScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: FilterViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {TopAppBar(
            title = "Filters",
            onBack = onNavigateBack
        )}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding() +16.dp , start = 16.dp , end = 16.dp)
        ) {

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

                        Text(
                            text = label,
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                        )
                        RadioButton(
                            selected = isSelected,
                            onClick = {
                                viewModel.onSortTypeSelected(sortType)
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = BlueMain
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.applySelection()
                    onNavigateBack()
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors().copy(containerColor = BlueMain)
            ) {
                Text(
                    text = "Apply",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}