package com.example.trustvault

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ThemeSelectionViewModel(private val userPreferencesManager: UserPreferencesManager) : ViewModel() {

    // MutableLiveData to store the current theme state
    private val _darkTheme = MutableStateFlow(false)
    val darkTheme: StateFlow<Boolean> = _darkTheme

    init {
        // Load the current theme state from UserPreferencesManager
        viewModelScope.launch {
            _darkTheme.value = userPreferencesManager.getCurrentTheme()
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
