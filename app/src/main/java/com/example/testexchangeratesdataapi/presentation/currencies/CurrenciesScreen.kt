package com.example.testexchangeratesdataapi.presentation.currencies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testexchangeratesdataapi.R
import com.example.testexchangeratesdataapi.presentation.common.TopAppBar
import com.example.testexchangeratesdataapi.presentation.currencies.components.BaseCurrencyDropdown
import com.example.testexchangeratesdataapi.presentation.common.CurrencyListItem
import com.example.testexchangeratesdataapi.presentation.currencies.components.state.CurrenciesUiState
import com.example.testexchangeratesdataapi.ui.theme.BlueMain
import com.example.testexchangeratesdataapi.ui.theme.White

@Composable
fun CurrenciesScreen(
    onNavigateToFilter: () -> Unit = {},
    viewModel: CurrenciesViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.initViewModel()
    }
    val uiState = viewModel.uiState.collectAsState().value
    Scaffold(
        topBar = {
            TopAppBar(
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        BaseCurrencyDropdown(
                            uiState = uiState,
                            onCurrencySelected = { viewModel.onBaseCurrencySelected(it) },
                            modifier = Modifier.weight(1f),
                        )

                        IconButton(
                            onClick = onNavigateToFilter,
                            modifier = Modifier.background(color = White, RoundedCornerShape (12.dp))
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.filter),
                                contentDescription = "Filter",
                                tint = BlueMain,
                            )
                        }
                    }
                },
                title = "Currencies"
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
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
                                textAlign = TextAlign.Center,
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
                        item {         Spacer(Modifier.height(16.dp))
                        }
                        items(uiState.items) { item ->
                            CurrencyListItem(
                                item = item,
                                onToggleFavorite = { viewModel.onToggleFavorite(item) },
                                text = item.code
                            )
                        }
                    }
                }
            }
        }
    }
}

