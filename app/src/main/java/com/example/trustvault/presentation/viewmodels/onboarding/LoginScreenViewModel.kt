package com.example.trustvault.presentation.viewmodels.onboarding

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue // This import has to be set manually in order for mutableStateOf to work. IntelliJ doesn't suggest it
import androidx.compose.runtime.setValue // This import has to be set manually in order for mutableStateOf to work. IntelliJ doesn't suggest it
import androidx.lifecycle.viewModelScope
import com.example.trustvault.data.encryption.EncryptionManager
import com.example.trustvault.domain.use_cases.BiometricLoginUseCase
import com.example.trustvault.domain.use_cases.LogInUseCase
import com.example.trustvault.presentation.screens.onboarding.UserPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.security.KeyStore
import javax.crypto.Cipher
import javax.inject.Inject

/**
 * ViewModel class for managing the state of the Login screen.
 *
 * This ViewModel stores the username and password entered by the user, as well as a computed property
 * that determines whether the login form is valid (i.e., both the username and password are non-blank).
 *
 * The state variables are `username` and `password`, both of which are mutable and are observed by the composables.
 * The `isFormValid` property checks whether the form can be submitted (i.e., the user has entered both a username and a password).
 *
 * @author Alex Álvarez de Sotomayor Sugimoto
 */
@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val encryptionManager: EncryptionManager,
    private val loginUseCase: LogInUseCase,
    private val biometricLoginUseCase: BiometricLoginUseCase
    ): ViewModel() {

    val darkTheme = userPreferencesManager.getCurrentTheme()
    val registrationStatus = userPreferencesManager.getCurrentRegistrationStatus()


    val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    val alias = "biometric_protected_key"
    val keyEntry = keyStore.getEntry(alias, null) as? KeyStore.SecretKeyEntry

    val secretKey = keyEntry?.secretKey

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var userIv: MutableState<ByteArray?> = mutableStateOf(ByteArray(0) { 0x00 })

    private val _loginResult = mutableStateOf<Boolean?>(null)
    val loginResult: State<Boolean?> = _loginResult

    val isFormValid: Boolean
        get() = email.isNotBlank() && password.isNotBlank()

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun initializeCipher(): Cipher {
        return encryptionManager.createDecryptionCipher(secretKey, userIv.value)
    }

    fun getUserIv() {
        viewModelScope.launch {
            val result =  biometricLoginUseCase.getUserIv()
             userIv.value = result.getOrNull()
        }
    }

    fun loginUser() {
        viewModelScope.launch {
            val result = loginUseCase.execute(email, password)

            if(result.isSuccess) {
                _loginResult.value = result.isSuccess
            } else {
                val exception = result.exceptionOrNull()
                val errorMsg = exception?.message ?: "Unknown error"
                _loginResult.value = result.isFailure
            }

        }
    }

    fun loginUserWithBiometrics(cipher: Cipher) {
        viewModelScope.launch {
            val result = biometricLoginUseCase.executeBiometricLogin(cipher, secretKey)

            if(result.isSuccess) {
                _loginResult.value = result.isSuccess
            } else {
                val exception = result.exceptionOrNull()
                val errorMsg = exception?.message ?: "Unknown error"
                _loginResult.value = result.isFailure
            }
        }
    }
}
