package com.example.trustvault.ui.screens

import android.content.Context
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.trustvault.R
import com.example.trustvault.ui.theme.DarkColorScheme
import com.example.trustvault.ui.theme.DarkModePrimaryGradient
import com.example.trustvault.ui.theme.LightColorScheme
import com.example.trustvault.ui.theme.LightModePrimaryGradient
import com.example.trustvault.ui.viewmodels.ThemeSelectionViewModel

class ThemeSelectionActivity() {

    /**
     * This composable function displays a screen allowing users to choose between light and dark modes
     *
     * @param onThemeUpdated A callback function that is invoked when the theme is updated. It returns nothing (Unit == void in Java)
     * @param userPreferences An instance of [UserPreferencesManager] to manage user preferences.
     */
    @Composable
    fun ThemeSelectionScreen(
        viewModel: ThemeSelectionViewModel,
    ) {
        val darkTheme by viewModel.darkTheme.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(if (darkTheme) DarkColorScheme.background else LightColorScheme.background),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Theme Title
            Text(
                text = "Elige un modo",
                fontSize = 40.sp,
                color = if (darkTheme) DarkColorScheme.onBackground else LightColorScheme.onBackground
            )

            // Theme description
            Text(
                text = "Presentamos el modo oscuro: \nuna interfaz elegante y amigable para la vista.",
                fontSize = 20.sp,
                color = if (darkTheme) DarkColorScheme.onBackground else LightColorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Theme image
            Image(
                painter = if (darkTheme) painterResource(id = R.drawable.img_dark_theme) else painterResource(id = R.drawable.img_light_theme),
                contentDescription = if (darkTheme) "Dark Mode Illustration" else "Light Mode Illustration",
                modifier = Modifier.size(350.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Theme Text
            Text(
                text = if (darkTheme) "Modo Oscuro" else "Modo Claro",
                color = if (darkTheme) DarkColorScheme.onBackground else LightColorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Theme switcher
            ThemeSwitcher(
                darkTheme = darkTheme,
                onClick = {
                    // Toggle theme via ViewModel
                    viewModel.toggleTheme()
                },
                size = 75.dp,
                padding = 5.dp
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Continue Button
            Button(
                onClick = { /* TODO: Go to SMS authentication activity */ },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Transparent as it is the same color of the background
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            if (darkTheme) DarkModePrimaryGradient else LightModePrimaryGradient,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Continuar", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }


    /**
     * Composable function that displays a toggle switch to switch between dark and light themes.
     *
     * @param darkTheme A boolean indicating whether the current theme is dark (true) or light (false).
     * @param size The size of the entire switch component.
     * @param padding Padding around the circle component inside.
     * @param onClick A callback function that is invoked when the switch is clicked to change the theme.
     */
    @Composable
    fun ThemeSwitcher(
        darkTheme: Boolean = false,
        size: Dp = 150.dp,
        padding: Dp = 10.dp,
        onClick: () -> Unit
    ) {
        val offset by animateDpAsState(
            targetValue = if (darkTheme) 0.dp else size,
            animationSpec = tween(durationMillis = 300)
        )

        Box(
            modifier = Modifier
                .width(size * 2) // Multiply by 2 since we have 2 icons
                .height(size)
                .clip(CircleShape)
                .clickable { onClick() }
                .background(if (darkTheme) Color(0XFF171717) else Color.White)
        ) {
            Box(
                modifier = Modifier
                    .size(size)
                    .offset(x = offset)
                    .padding(all = padding)
                    .clip(CircleShape)
                    .background(if (darkTheme) DarkColorScheme.secondary else LightColorScheme.secondary)
            )
            Row(
                modifier = Modifier
                    .border(BorderStroke(1.dp, Color(0xFF282D37)))
            ) {
                Box(
                    modifier = Modifier.size(size),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(size / 3),
                        painter = painterResource(id = R.drawable.ic_moon),
                        contentDescription = "Dark Mode",
                        tint = if (darkTheme) DarkColorScheme.onBackground else LightColorScheme.onBackground
                    )
                }

                Box(
                    modifier = Modifier.size(size),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(size / 3),
                        painter = painterResource(id = R.drawable.ic_sun),
                        contentDescription = "Light Mode",
                        tint = DarkColorScheme.onBackground
                    )
                }
            }
        }
    }
}

/**
 * This function is used purely for previewing the ThemeSelectionScreen composable.
 */
@Preview(showBackground = true)
@Composable
fun PreviewThemeSelectionScreen() {
    val context = LocalContext.current
    val themeSelectionActivity = ThemeSelectionActivity()
    val mockUserPreferencesManager = UserPreferencesManager(context)
    val mockViewModel = ThemeSelectionViewModel(mockUserPreferencesManager)

    themeSelectionActivity.ThemeSelectionScreen(
        viewModel = mockViewModel,
    )
}