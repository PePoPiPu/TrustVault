package com.example.trustvault.ui.screens

import android.os.Bundle
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.State
import com.example.trustvault.R
import com.example.trustvault.ui.theme.DarkColorScheme
import com.example.trustvault.ui.theme.DarkModePrimaryGradient
import com.example.trustvault.ui.theme.LightColorScheme
import com.example.trustvault.ui.theme.LightModePrimaryGradient
import com.example.trustvault.ui.theme.TrustVaultTheme

class GetStartedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            TrustVaultTheme {
                GetStartedScreen(darkTheme = true)
            }
        }
    }
    /**
     * Displays the login screen with a logo, title, and authentication buttons.
     *
     * @param darkTheme Boolean indicating if the app is in dark mode.
     *
     * @author Alex Sugimoto
     */
    @Composable // Allows us to create a Composable object
    fun GetStartedScreen(
        onLoginClick: () -> Unit = {},
        onRegisterClick: () -> Unit = {},
        darkTheme: Boolean
    ) {
        Column (
            modifier = Modifier // Defining the attributes for this column
                .fillMaxSize() // Make the layout fill all available space
                .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background),
            verticalArrangement = Arrangement.Center, // Vertical Alignment
            horizontalAlignment = Alignment.CenterHorizontally // Horizontal Alignment
        ) {
            // Logo
            Image(
                painter = if (darkTheme) painterResource(id = R.drawable.ic_trustvault) else painterResource(id = R.drawable.ic_trustvault_black), // Paints the resource
                contentDescription = "TrustVault Logo", // Content Description for impaired individuals
                modifier = Modifier.size(100.dp)
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Trust",
                    color = if (darkTheme) DarkColorScheme.onBackground else LightColorScheme.onBackground,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 32.dp)
                )
                Text(
                    text = "Vault",
                    color = if (darkTheme) DarkColorScheme.onBackground else LightColorScheme.onBackground,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 32.dp)
                )
            }

            // Login Button
            OutlinedButton(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                ,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (darkTheme) DarkColorScheme.onBackground else LightColorScheme.onBackground
                )
            ) {
                Text(
                    text = "Iniciar sesión",
                    color = if (darkTheme) DarkColorScheme.onBackground else LightColorScheme.onBackground,
                    fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp)) // Gives space between elements

            // Register Button + Gradient
            Button(
                onClick = onRegisterClick,
                modifier = Modifier
                    .fillMaxWidth(0.8f) // resulting MaxWidth = MaxWidth * fraction AKA reduce max space
                    .height(50.dp), // Height of the button
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
                    Text("Regístrate", color = Color.White, fontSize = 16.sp)
                }
            }

        }
    }

    @Composable
    @Preview
    fun LoginScreenPreview() {
        var darkTheme by remember { mutableStateOf(false) } // This is to be set in the main activity. Set here for testing
        GetStartedScreen (
            darkTheme = !darkTheme
        )
    }
}