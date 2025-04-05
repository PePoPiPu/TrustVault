package com.example.trustvault.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustvault.ui.screens.onboarding.UserPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetStartedViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {

    private val _darkTheme = MutableLiveData<Boolean>()
    val darkTheme: LiveData<Boolean> get() = _darkTheme

    init {
        // Launch a coroutine to collect from the Flow
        viewModelScope.launch {
            userPreferencesManager.darkThemeFlow.collect { isDarkMode ->
                _darkTheme.value = isDarkMode
            }
        }
    }
}

