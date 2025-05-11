package com.example.trustvault.presentation.viewmodels.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustvault.domain.models.StoredAccount
import com.example.trustvault.domain.use_cases.AddAccountUseCase
import com.example.trustvault.presentation.screens.onboarding.UserPreferencesManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val addAccountUseCase: AddAccountUseCase
) : ViewModel() {
    val darkTheme = userPreferencesManager.getCurrentTheme()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()


    var platformName by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    fun addAccount() {
        val newAccount = StoredAccount(
            platformName = platformName,
            storedEmail = email,
            storedPassword = password
        )

        viewModelScope.launch {
            val result = addAccountUseCase.addNewAccount(currentUserId, newAccount)
        }
    }
}