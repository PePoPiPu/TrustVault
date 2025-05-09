package com.example.trustvault.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
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

    fun isFormValid(): Boolean {
        return code.all { it.isNotBlank() }
    }

    var storedVerificationId: String? = null

    fun authorizeUser(context: Context, phoneNumber: String) {
        viewModelScope.launch {
            val result = smsAuthUseCase.executeCodeSend(context, parsedPhoneNumber)
            if(result.isSuccess) {
                storedVerificationId = result.getOrNull().toString()
            }else {
                // Handle failure in sending the SMS
                Log.e("TAG", "SMS sending failed: ${result.exceptionOrNull()}")
            }
        }
    }

    private val _verificationResult = mutableStateOf<Boolean?>(null)
    val verificationResult: State<Boolean?> = _verificationResult

    fun verifyCode(context: Context) {
        viewModelScope.launch {
            if (storedVerificationId != null) {
                Log.d("VERID", storedVerificationId.toString())
                Log.d("CODE", code.joinToString(""))
                // code.toString() ->  SnapshotStateList(value=[1, 2, 3, 4, 5, 6])@152215220
                val result = smsAuthUseCase.executeVerification(storedVerificationId!!, code.joinToString(""), context)
                _verificationResult.value = result.isSuccess
            } else {
                Log.e("TAG", "Verification ID is null!")
            }
        }
    }
}