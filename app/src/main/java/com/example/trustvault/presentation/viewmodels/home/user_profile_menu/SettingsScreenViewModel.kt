package com.example.trustvault.presentation.viewmodels.home.user_profile_menu

import androidx.lifecycle.ViewModel
import com.example.trustvault.presentation.screens.onboarding.UserPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {
    val darkTheme = userPreferencesManager.getCurrentTheme()
}