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

/**
 * ViewModel for the Loader screen that manages UI state and delayed navigation.
 *
 * This ViewModel waits a fixed amount of time before triggering navigation to the next screen.
 * It also exposes the current theme setting from [UserPreferencesManager].
 *
 * @property userPreferencesManager Manages user theme preferences and other onboarding-related settings.
 */
@HiltViewModel
class LoaderScreenViewModel @Inject constructor(userPreferencesManager: UserPreferencesManager): ViewModel() {

    val darkTheme = userPreferencesManager.getCurrentTheme()

    private val _navigateToNextScreen = MutableStateFlow(false)
    val navigateToNextScreen: StateFlow<Boolean> = _navigateToNextScreen

    /**
     * Launches a coroutine that waits for 3 seconds before updating [navigateToNextScreen]
     * to trigger a navigation event from the UI.
     */
    fun waitForSeconds() {
        viewModelScope.launch {
            delay(3000) // Wait for 3 seconds
            _navigateToNextScreen.value = true // Trigger navigation
        }
    }
}
