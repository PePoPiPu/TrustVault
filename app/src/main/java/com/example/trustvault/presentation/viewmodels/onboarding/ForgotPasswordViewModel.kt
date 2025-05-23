package com.example.trustvault.presentation.viewmodels.onboarding

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustvault.domain.use_cases.ForgotPasswordUseCase
import com.example.trustvault.presentation.screens.onboarding.UserPreferencesManager
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for handling forgot password functionality.
 *
 * This ViewModel manages the user input for the email field and communicates with
 * the [ForgotPasswordUseCase] to initiate a password reset email. It also handles
 * result state and error messages, and provides the current theme preference.
 */
@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    userPreferencesManager: UserPreferencesManager,
    private val forgotPasswordUseCase: ForgotPasswordUseCase
    ) : ViewModel() {

    val darkTheme = userPreferencesManager.getCurrentTheme()

    var email by mutableStateOf("")
    var errorMsg by mutableStateOf("")

    private val _sendPassResult = mutableStateOf<Boolean?>(null)
    val senPassResult: State<Boolean?> = _sendPassResult

    val isFormValid: Boolean
        get () = email.isNotBlank()

    /**
     * Sends a password reset email to the entered address using the use case.
     *
     * Updates [senPassResult] with the outcome and sets appropriate [errorMsg] if needed.
     */
    fun sendPasswordEmail() {
        viewModelScope.launch {

            val result = forgotPasswordUseCase.execute(email)

            if (result.isSuccess) {
                _sendPassResult.value = result.isSuccess
            } else {
                errorMsg = when (val exception = result.exceptionOrNull()) {
                    null -> "Correo de recuperación enviado"
                    is FirebaseAuthInvalidUserException -> "El correo no corresponde a un usuario registrado"
                    else -> exception.message ?: "Error desconocido"

                }
                _sendPassResult.value = result.isFailure

            }

            }
        }
    }
