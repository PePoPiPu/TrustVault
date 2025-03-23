package com.example.trustvault.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustvault.ui.screens.UserPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * This ViewModel is responsible for managing and toggling the app's theme (dark or light).
 *
 * This ViewModel interacts with the [UserPreferencesManager] to retrieve and store the user's theme preference.
 * It exposes a [StateFlow] that provides the current theme state, which can be observed to reflect changes in the UI.
 *
 * @property userPreferencesManager The [UserPreferencesManager] used to load and save theme preferences.
 * @author Alex Sugimoto
 */
class ThemeSelectionViewModel(private val userPreferencesManager: UserPreferencesManager) : ViewModel() {

    // MutableLiveData to store the current theme state
    private val _darkTheme = MutableStateFlow(false)
    val darkTheme: StateFlow<Boolean> = _darkTheme

    init { // Init allows code inside to execute once the class is initiated before anything else
        viewModelScope.launch { // Starts a coroutine in the scope of the ViewModel (if the ViewModel is cleared, the coroutines are cancelled
            _darkTheme.value = userPreferencesManager.getCurrentTheme() // Gets the value of the current theme
        }
    }

    // Function to toggle the theme and save it in preferences
    fun toggleTheme() {
        viewModelScope.launch {
            val newTheme = !_darkTheme.value
            userPreferencesManager.saveDarkTheme(newTheme)
            _darkTheme.value = newTheme
        }
    }
}