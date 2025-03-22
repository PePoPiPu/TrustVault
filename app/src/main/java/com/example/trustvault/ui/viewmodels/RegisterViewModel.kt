package com.example.trustvault.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    var email by mutableStateOf("")
    var username by mutableStateOf("")
    var phone by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    val isFormValid: Boolean
        get() = email.isNotBlank() && username.isNotBlank() && phone.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
}