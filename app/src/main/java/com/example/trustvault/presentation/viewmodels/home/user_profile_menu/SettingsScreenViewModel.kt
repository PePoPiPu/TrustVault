package com.example.trustvault.presentation.viewmodels.home.user_profile_menu

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustvault.domain.use_cases.LogoutUseCase
import com.example.trustvault.presentation.screens.onboarding.UserPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    val darkTheme = userPreferencesManager.getCurrentTheme()
    fun logout(context: Context) {
        val activity = context as? Activity
        viewModelScope.launch {
            logoutUseCase.logOut()
            activity?.finish()
        }
    }
}