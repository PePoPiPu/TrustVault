package com.example.trustvault.presentation.screens.onboarding

import android.annotation.SuppressLint

import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import javax.crypto.Cipher

@SuppressLint("RestrictedApi")
@Composable
fun BiometricLoginScreen(
    onSuccess: (Cipher) -> Unit = {},
    cryptoObject: BiometricPrompt.CryptoObject
) {
    val context = LocalContext.current
    val activity = context as FragmentActivity // -> as statement does smart type casting. Could be used with a safe cast "as?" to return null if casting fails
    val showPrompt = remember { mutableStateOf(true) }
    val executor = remember { ContextCompat.getMainExecutor(context) } // An object that executes submitted Runnable tasks, executes a task in  the thread from which is called

    if(showPrompt.value) {
        LaunchedEffect(Unit) {
            val promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                .setTitle("Desbloquea TrustVault")
                .setSubtitle("Inicia sesión con tu huella")
                .setNegativeButtonText("Cancelar")
                .build()

            val biometricPrompt = BiometricPrompt(
                activity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        showPrompt.value = false
                        val cipher = result.cryptoObject?.cipher
                        if(cipher != null) {
                            onSuccess(cipher)
                        }
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        showPrompt.value = false
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                    }
                }
            )
            biometricPrompt.authenticate(promptInfo, cryptoObject)
        }
    }
}