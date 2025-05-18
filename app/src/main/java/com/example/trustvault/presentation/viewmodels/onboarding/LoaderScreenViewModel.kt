package com.example.trustvault.presentation.viewmodels.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustvault.presentation.screens.onboarding.UserPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoaderScreenViewModel @Inject constructor(userPreferencesManager: UserPreferencesManager): ViewModel() {

    val darkTheme = userPreferencesManager.getCurrentTheme()

    private val _navigateToNextScreen = MutableStateFlow(false)
    val navigateToNextScreen: StateFlow<Boolean> = _navigateToNextScreen

    fun waitForSeconds() {
        viewModelScope.launch {
            delay(3000) // Wait for 3 seconds
            _navigateToNextScreen.value = true // Trigger navigation
        }
    }
}
