package com.example.trustvault.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustvault.domain.use_cases.SMSAuthUseCase
import com.example.trustvault.presentation.screens.onboarding.UserPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SMSAuthScreenViewModel @Inject constructor(
    userPreferencesManager: UserPreferencesManager,
    private val smsAuthUseCase: SMSAuthUseCase
    ) : ViewModel(){
    val darkTheme = userPreferencesManager.getCurrentTheme()
    var code = mutableStateListOf("", "", "", "", "", "")
    var phone by mutableStateOf("")
    var parsedPhoneNumber by mutableStateOf("")
    fun authorizeUser(context: Context, phoneNumber: String) {
        viewModelScope.launch {
            // Log.d("SMSVIEWMODEL", parsedPhoneNumber)
            smsAuthUseCase.execute(context, parsedPhoneNumber)
            smsAuthUseCase.executeVerification(context, code.toString())
        }
    }
}