package com.example.trustvault.presentation.viewmodels.onboarding

import android.accounts.Account
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustvault.domain.models.StoredAccount
import com.example.trustvault.domain.models.User
import com.example.trustvault.domain.use_cases.RegisterUseCase
import com.example.trustvault.presentation.screens.onboarding.UserPreferencesManager
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val registerUseCase: RegisterUseCase
    ) : ViewModel() {

    val darkTheme = userPreferencesManager.getCurrentTheme()

    var email by mutableStateOf("")
    var username by mutableStateOf("")
    var phone by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    private val _registrationResult = MutableLiveData<Result<Unit>>()
    val registrationResult: LiveData<Result<Unit>> = _registrationResult
    val isFormValid: Boolean
        get() = email.isNotBlank() && username.isNotBlank() && phone.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()

    fun register() {
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
            password = password
        )

        viewModelScope.launch {
            val result = registerUseCase.execute(user)
            _registrationResult.value = result
        }

    }

    // Email validation function
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Password validation function
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

    // countryCode: ISO 3166-1 alpha-2 code (e.g., "us", "es", "fr")
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