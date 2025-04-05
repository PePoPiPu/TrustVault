package com.example.trustvault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.trustvault.ui.navigation.AppNavHost
import com.example.trustvault.ui.theme.TrustVaultTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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