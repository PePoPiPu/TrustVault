package com.example.trustvault

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.example.trustvault.presentation.navigation.AppNavHost
import com.example.trustvault.presentation.theme.TrustVaultTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        System.setProperty("jna.boot.library.path", applicationContext.filesDir.path + "/lib/arm64-v8a")

        // Make the bars transparent (modern way)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

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