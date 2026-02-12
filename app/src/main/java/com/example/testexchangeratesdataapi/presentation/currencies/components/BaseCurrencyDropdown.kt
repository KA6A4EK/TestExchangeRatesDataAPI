package com.example.testexchangeratesdataapi.presentation.currencies.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.testexchangeratesdataapi.R
import com.example.testexchangeratesdataapi.presentation.currencies.components.state.CurrenciesUiState
import com.example.testexchangeratesdataapi.ui.theme.BlueMain
import com.example.testexchangeratesdataapi.ui.theme.PrimaryLight
import com.example.testexchangeratesdataapi.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseCurrencyDropdown(
    uiState: CurrenciesUiState,
    onCurrencySelected: (String) -> Unit,
    modifier: Modifier = Modifier.Companion,
) {
    var expanded by remember { mutableStateOf(false) }

    val successState = uiState as? CurrenciesUiState.Success
    val selected = successState?.baseCurrency ?: "USD"

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.Companion
                .menuAnchor()
                .fillMaxWidth()
                .background(
                    color = Color.Companion.White,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.Companion.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = selected,
                style = MaterialTheme.typography.titleMedium,
            )
            Icon(
                painterResource(if (!expanded) R.drawable.dropdown else R.drawable.dropup), contentDescription = "", tint = BlueMain
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.Companion.background(color = White)
        ) {
            successState?.items?.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.code) },
                    onClick = {
                        expanded = false
                        onCurrencySelected(item.code)
                    },
                    modifier = Modifier.Companion.background(color = if (item.code == uiState.baseCurrency) PrimaryLight else White)
                )
            }
        }
    }
}

