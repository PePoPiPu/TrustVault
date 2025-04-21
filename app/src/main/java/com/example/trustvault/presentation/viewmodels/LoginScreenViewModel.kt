package com.example.trustvault.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue // This import has to be set manually in order for mutableStateOf to work. IntelliJ doesn't suggest it
import androidx.compose.runtime.setValue // This import has to be set manually in order for mutableStateOf to work. IntelliJ doesn't suggest it
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.trustvault.domain.models.User
import com.example.trustvault.domain.use_cases.LogInUseCase
import com.example.trustvault.presentation.screens.onboarding.UserPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
    userPreferencesManager: UserPreferencesManager,
    private val loginUseCase: LogInUseCase
    ): ViewModel() {

    val darkTheme = userPreferencesManager.getCurrentTheme()

    var username by mutableStateOf("")
    var password by mutableStateOf("")

    private val _loginResult = MutableLiveData<Result<User>?>()
    val loginResult: LiveData<Result<User>?> = _loginResult

    val isFormValid: Boolean
        get() = username.isNotBlank() && password.isNotBlank()

    fun loginUser() {
        viewModelScope.launch {
            val result = loginUseCase.execute(username, password)
            _loginResult.value = result
        }
    }
}
