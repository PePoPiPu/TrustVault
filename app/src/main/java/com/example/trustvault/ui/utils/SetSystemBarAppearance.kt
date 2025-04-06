package com.example.trustvault.ui.utils

import android.app.Activity
import android.os.Build
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun SetSystemBarsAppearance(darkTheme: Boolean) {
    val context = LocalContext.current
    val activity = context as? Activity ?: return

    SideEffect {
        val window = activity.window
        WindowCompat.setDecorFitsSystemWindows(window, false) // allows content to draw behind bars

        val controller = WindowInsetsControllerCompat(window, window.decorView)

        controller.isAppearanceLightStatusBars = !darkTheme
        controller.isAppearanceLightNavigationBars = !darkTheme

        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT
    }
}
