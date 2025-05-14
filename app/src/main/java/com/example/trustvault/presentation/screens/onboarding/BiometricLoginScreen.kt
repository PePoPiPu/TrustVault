package com.example.trustvault.presentation.screens.onboarding

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ComponentActivity

@SuppressLint("RestrictedApi")
@Composable
fun BiometricLoginScreen(
    onSuccess: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as ComponentActivity
    val showPrompt = remember { mutableStateOf(false) }

    if(showPrompt.value) {
        LaunchedEffect(Unit) {
            val promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                .setTitle("Desbloquea TrustVault")
                .setSubtitle("Inicia sesión con tu huella dactilar")
                .setNegativeButtonText("Cancelar")


        }
    }
}