package com.example.trustvault.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// 🌙 Dark Mode Colors
val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBC39DB),
    secondary = Color(0xFF1C1A26),
    tertiary = Color(0xFFE7A3F8),
    background = Color(0xFF15131D),
    surface = Color(0xFF111111),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFFF0F0F1), // Text/Icons
    onSurface = Color(0xFFF0F0F1)
)

// ☀️ Light Mode Colors
val LightColorScheme = lightColorScheme(
    primary = Color(0xFFCFA1FF),
    secondary = Color(0xFF5F74FF),
    tertiary = Color(0xFF88FFD9),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFF1C1A26),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color(0xFF202020), // Text/Icons
    onSurface = Color(0xFF202020)
)

// 🎨 Gradient for Primary Button
val LightModePrimaryGradient = Brush.linearGradient(
    colors = listOf(Color(0xFFCFA1FF), Color(0xFF5F74FF), Color(0xFF88FFD9))
)

val DarkModePrimaryGradient = Brush.linearGradient(
    colors = listOf(Color(0xFFBC39DB), Color(0xFF7C8EFF), Color(0xFFE7A3F8))
)

val DisabledButtonGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF8C8C8C), Color(0xFF6C6C6C), Color(0xFFCECECE))
)

val TransparentGradient = Brush.linearGradient(
    colors = listOf(Color(0x00BC39DB), Color(0x007C8EFF), Color(0x00E7A3F8))
)

@Composable
fun TrustVaultTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
