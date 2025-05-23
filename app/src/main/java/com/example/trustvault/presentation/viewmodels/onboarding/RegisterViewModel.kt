package com.example.trustvault.presentation.viewmodels.onboarding

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustvault.data.encryption.EncryptionManager
import com.example.trustvault.domain.models.User
import com.example.trustvault.domain.use_cases.RegisterUseCase
import com.example.trustvault.presentation.screens.onboarding.UserPreferencesManager
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.security.KeyStore
import javax.crypto.Cipher
import javax.inject.Inject

/**
 * ViewModel responsible for managing the user registration process, including form validation,
 * secure key handling with Android Keystore, and password encryption using a cipher.
 *
 * @property userPreferencesManager Manages user settings and registration state.
 * @property registerUseCase Handles business logic for registering a new user.
 * @property encryptionManager Provides cipher for encrypting sensitive information.
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val registerUseCase: RegisterUseCase,
    private val encryptionManager: EncryptionManager
    ) : ViewModel() {

    val darkTheme = userPreferencesManager.getCurrentTheme()

    val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    val alias = "biometric_protected_key"
    val keyEntry = keyStore.getEntry(alias, null) as? KeyStore.SecretKeyEntry

    val secretKey = keyEntry?.secretKey

    fun initializeCipher(): Cipher {
        return encryptionManager.createEncryptionCipher(secretKey)
    }

    var email by mutableStateOf("")
    var username by mutableStateOf("")
    var phone by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    private val _registrationResult = MutableLiveData<Result<Unit>>()
    val registrationResult: LiveData<Result<Unit>> = _registrationResult
    val isFormValid: Boolean
        get() = email.isNotBlank() && username.isNotBlank() && phone.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()

    /**
     * Register a new user with the encryption cipher.
     *
     * @param cipher The cipher used to encrypt the user's password or key.
     */
    fun register(cipher: Cipher) {
        if(!isFormValid) {
            _registrationResult.value = Result.failure(Exception("All fields are required"))
            return
        }

        if(password != confirmPassword) {
            _registrationResult.value = Result.failure(Exception("Passwords do not match"))
            return
        }

        val user = User(
            email = email,
            username = username,
            phone = phone,
            password = password,
            encryptedKey = null,
            iv =  null
        )

        viewModelScope.launch {
            val result = registerUseCase.execute(user, cipher)
            if(result.isSuccess) {
                userPreferencesManager.saveIsRegistered(true)
                userPreferencesManager.saveAuthType(true)
            }
            _registrationResult.value = result
        }
    }

    /**
     * Validates the email format.
     *
     * @param email The email string to validate.
     * @return True if the email is valid, false otherwise.
     */
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Validates the password based on common security rules.
     *
     * @param password The password string to validate.
     * @return An error message string if the password is invalid, or null if it's valid.
     */
    fun validatePassword(password: String): String? {
        return when {
            password.length < 8 -> "Password must be at least 8 characters long."
            !password.any { it.isUpperCase() } -> "Password must contain at least one uppercase letter."
            !password.any { it.isLowerCase() } -> "Password must contain at least one lowercase letter."
            !password.any { it.isDigit() } -> "Password must contain at least one digit."
            !password.any { it in "!@#$%^&*()-_=+[]{}|;:'\",.<>?/`~" } -> "Password must contain at least one special character."
            else -> null // Valid password
        }
    }

    /**
     * Validates the phone number based on the given country code.
     *
     * @param countryCode ISO 3166-1 alpha-2 country code (e.g., "US", "ES").
     * @param phoneNumber The phone number string to validate.
     * @return True if the phone number is valid for the given country, false otherwise.
     */
    fun validatePhoneNumber(countryCode: String?, phoneNumber: String) : Boolean {
        if(phoneNumber.isBlank()) return false
        val phoneUtil = PhoneNumberUtil.getInstance()

        return try {
            val parsedNumber = phoneUtil.parse(phoneNumber, countryCode?.uppercase())

            phoneUtil.isValidNumber(parsedNumber)
        } catch (e: NumberParseException) {
            false
        }
    }
}