package com.example.testexchangeratesdataapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.testexchangeratesdataapi.presentation.common.CurrencyTrackerRoot
import com.example.testexchangeratesdataapi.ui.theme.TestExchangeRatesDataAPITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestExchangeRatesDataAPITheme {
                CurrencyTrackerRoot()
            }
        }
    }
}

