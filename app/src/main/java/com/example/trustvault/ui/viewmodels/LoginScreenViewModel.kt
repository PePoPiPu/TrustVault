package com.example.trustvault.ui.viewmodels

import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue // This import has to be set manually in order for mutableStateOf to work. IntelliJ doesn't suggest it
import androidx.compose.runtime.setValue // This import has to be set manually in order for mutableStateOf to work. IntelliJ doesn't suggest it

class LoginScreenViewModel : ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")

    val isFormValid: Boolean
        get() = username.isNotBlank() && password.isNotBlank()

}
