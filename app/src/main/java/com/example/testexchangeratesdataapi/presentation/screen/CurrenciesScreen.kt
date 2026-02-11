package com.example.testexchangeratesdataapi.presentation.screen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testexchangeratesdataapi.presentation.state.CurrenciesUiState
import com.example.testexchangeratesdataapi.presentation.state.CurrencyItem
import com.example.testexchangeratesdataapi.presentation.viewmodel.CurrenciesViewModel

@Composable
fun CurrenciesScreen(
    onNavigateToFilter: () -> Unit = {},
    viewModel: CurrenciesViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Currencies",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Base Currency Selector and Filter Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BaseCurrencyDropdown(
                uiState = uiState,
                onCurrencySelected = {viewModel.onBaseCurrencySelected(it)},
                modifier = Modifier.weight(1f)
            )

            // Filter Button
            IconButton(
                onClick = onNavigateToFilter,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Filter",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content
        when (uiState) {
            is CurrenciesUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Загрузка...")
                }
            }

            is CurrenciesUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = uiState.message,
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(
                            onClick = { viewModel.retry() }
                        ) {
                            Text("Повторить")
                        }
                    }
                }
            }

            is CurrenciesUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.items) { item ->
                        CurrencyListItem(
                            item = item,
                            onToggleFavorite = { viewModel.onToggleFavorite(item) }
                        )
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseCurrencyDropdown(
    uiState: CurrenciesUiState,
    onCurrencySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val currencies = listOf("USD", "EUR", "GBP", "JPY", "CNY", "RUB", "AED", "BYN")
    var expanded by remember { mutableStateOf(false) }

    val selected = (uiState as? CurrenciesUiState.Success)?.baseCurrency ?: "EUR"

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            value = selected,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            currencies.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        onCurrencySelected(it)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun CurrencyListItem(
    item: CurrencyItem,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Currency Code
            Text(
                text = item.code,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            // Quote
            Text(
                text = String.format("%.6f", item.quote),
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Favorite Star
            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Toggle favorite",
                    tint = if (item.isFavorite) Color(0xFFFFD700) else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
