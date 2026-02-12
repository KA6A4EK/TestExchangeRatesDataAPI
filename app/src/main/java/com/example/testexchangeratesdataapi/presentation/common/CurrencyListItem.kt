package com.example.testexchangeratesdataapi.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testexchangeratesdataapi.R
import com.example.testexchangeratesdataapi.presentation.currencies.components.state.CurrencyItem
import com.example.testexchangeratesdataapi.ui.theme.BlueMain
import com.example.testexchangeratesdataapi.ui.theme.Lavender
import com.example.testexchangeratesdataapi.ui.theme.Outline
import com.example.testexchangeratesdataapi.ui.theme.Yellow

@Composable
fun   CurrencyListItem(
    item: CurrencyItem,
    onToggleFavorite: () -> Unit,
    text : String,
) {
    Card(
        modifier = Modifier.Companion.fillMaxWidth(),
        colors = CardDefaults.cardColors().copy(containerColor = Outline)

    ) {
        Row(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Companion.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Companion.Medium,
                modifier = Modifier.Companion.weight(1f)
            )

            Text(
                text = String.format("%.6f", item.quote),
                fontSize = 16.sp,
                modifier = Modifier.Companion.padding(horizontal = 16.dp)
            )

            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier.Companion.size(40.dp)
            ) {
                if (item.isFavorite)
                    Icon(
                        painter = painterResource(R.drawable.favorites),
                        contentDescription = "Toggle favorite",
                        tint = Yellow,
                        modifier = Modifier.Companion.size(24.dp)
                    )
                else
                    Icon(
                        painter = painterResource(R.drawable.favorites_off),
                        contentDescription = "Toggle favorite",
                        modifier = Modifier.Companion.size(24.dp),
                        tint = Lavender,

                        )
            }
        }
    }
}