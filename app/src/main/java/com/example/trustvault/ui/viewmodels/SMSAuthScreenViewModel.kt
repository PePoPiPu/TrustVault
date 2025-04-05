package com.example.trustvault.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.trustvault.ui.screens.onboarding.UserPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SMSAuthScreenViewModel @Inject constructor(userPreferencesManager: UserPreferencesManager) : ViewModel(){
    val darkTheme = userPreferencesManager.getCurrentTheme()
}