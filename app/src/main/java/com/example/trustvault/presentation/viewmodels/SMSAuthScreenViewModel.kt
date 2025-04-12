package com.example.trustvault.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.trustvault.presentation.screens.onboarding.UserPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SMSAuthScreenViewModel @Inject constructor(userPreferencesManager: UserPreferencesManager) : ViewModel(){
    val darkTheme = userPreferencesManager.getCurrentTheme()
    var digit by mutableStateOf("")
}