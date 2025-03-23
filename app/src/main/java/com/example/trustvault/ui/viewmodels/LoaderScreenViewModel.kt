package com.example.trustvault.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoaderScreenViewModel : ViewModel() {

    private val _navigateToNextScreen = MutableStateFlow(false)
    val navigateToNextScreen: StateFlow<Boolean> = _navigateToNextScreen

    fun waitForSeconds() {
        viewModelScope.launch {
            delay(3000) // Wait for 3 seconds
            _navigateToNextScreen.value = true // Trigger navigation
        }
    }
}
