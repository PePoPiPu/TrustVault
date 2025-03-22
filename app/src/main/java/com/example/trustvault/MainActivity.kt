package com.example.trustvault

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.trustvault.ui.navigation.AppNavHost
import com.example.trustvault.ui.theme.TrustVaultTheme
import com.example.trustvault.ui.viewmodels.ThemeViewModel


class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrustVaultTheme {
                Surface (
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavHost(startDestination = "GetStartedActivity")
                }
            }
        }
    }
}