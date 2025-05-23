package com.example.trustvault.presentation.viewmodels.onboarding

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

/**
 * ViewModel for managing SMS authentication during the onboarding process.
 *
 * This ViewModel handles sending verification codes and validating them using the provided phone number.
 *
 * @property userPreferencesManager Used to retrieve the current theme settings.
 * @property smsAuthUseCase Use case for sending and verifying SMS codes.
 */
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

    /**
     * Sends a verification code to the user's phone.
     *
     * @param context The context used for sending the SMS.
     * @param phoneNumber The phone number to send the verification code to.
     */
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

    /**
     * Verifies the user's SMS code using the stored verification ID.
     *
     * @param context The context used during the verification process.
     */
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