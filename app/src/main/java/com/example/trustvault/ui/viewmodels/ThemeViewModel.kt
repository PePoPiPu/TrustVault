package com.example.trustvault.ui.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustvault.ui.screens.UserPreferencesManager
import kotlinx.coroutines.launch

/**
 * ViewModel that interacts with [UserPreferencesManager] to manage theme preferences.
 */
class ThemeViewModel(private val context: Context) : ViewModel() {

    constructor() : this(Application())

    private val userPreferencesManager = UserPreferencesManager(context)

    // LiveData is used to expose the current theme state to the UI
    private val _isDarkTheme = MutableLiveData<Boolean>()
    val isDarkTheme: LiveData<Boolean> get() = _isDarkTheme

    init {
        // Collect the current theme setting and update the LiveData
        viewModelScope.launch {
            userPreferencesManager.darkThemeFlow.collect { isDarkMode ->
                _isDarkTheme.value = isDarkMode
            }
        }
    }

    /**
     * Save the selected theme in preferences.
     */
    fun setDarkTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            userPreferencesManager.saveDarkTheme(isDarkMode)
        }
    }

    /**
     * Retrieve the current theme synchronously for initial setup (for example, during app launch).
     */
    fun getCurrentTheme(): Boolean {
        return userPreferencesManager.getCurrentTheme()
    }
}