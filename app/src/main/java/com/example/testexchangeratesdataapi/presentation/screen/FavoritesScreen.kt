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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testexchangeratesdataapi.presentation.state.FavoritesUiState
import com.example.testexchangeratesdataapi.presentation.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Favorites",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Content
        when (uiState) {
            is FavoritesUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Загрузка...")
                }
            }

            is FavoritesUiState.Error -> {
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
                    }
                }
            }

            is FavoritesUiState.Success -> {
                if (uiState.favorites.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Нет избранных валютных пар",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.favorites) { pair ->
                            FavoriteListItem(
                                pair = pair,
                                onToggleFavorite = {
                                    viewModel.onToggleFavorite(
                                        pair.baseCurrency,
                                        pair.targetCurrency
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoriteListItem(
    pair: com.example.testexchangeratesdataapi.presentation.state.FavoritePair,
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
            // Currency Pair Code
            Text(
                text = pair.pairCode,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            // Quote
            Text(
                text = String.format("%.6f", pair.quote),
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Favorite Star (always filled for favorites)
            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Remove from favorites",
                    tint = Color(0xFFFFD700), // Gold color
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
