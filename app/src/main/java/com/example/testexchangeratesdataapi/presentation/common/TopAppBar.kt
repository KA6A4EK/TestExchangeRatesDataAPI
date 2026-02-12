package com.example.testexchangeratesdataapi.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.testexchangeratesdataapi.ui.theme.Background

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    onBack: (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null,
    title: String,
) {
    Column() {
        TopAppBar(
            title = {
                Column() {
                    Text(
                        title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Companion.Bold
                    )


                }
            },
            navigationIcon = {
                if (onBack != null) IconButton(
                    onClick = onBack
                ) { Icon(Icons.Default.ArrowBack, contentDescription = "Back Icon") }
            },
            colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = Background)
        )
        if (content != null) {
            Column(
                modifier = Modifier.Companion.background(Background)
                    .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
            ) {
                content()
            }
        }
    }
}