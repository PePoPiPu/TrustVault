package com.example.trustvault.presentation.viewmodels.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustvault.data.encryption.EncryptionManager
import com.example.trustvault.domain.use_cases.AddAccountUseCase
import com.example.trustvault.domain.use_cases.BiometricLoginUseCase
import com.example.trustvault.presentation.screens.onboarding.UserPreferencesManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.security.KeyStore
import javax.crypto.Cipher
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val addAccountUseCase: AddAccountUseCase,
    private val biometricLoginUseCase: BiometricLoginUseCase,
    private val encryptionManager: EncryptionManager
) : ViewModel() {
    val darkTheme = userPreferencesManager.getCurrentTheme()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()

    var platformName by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var userIv: MutableState<ByteArray?> = mutableStateOf(ByteArray(0) { 0x00 })

    val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    val alias = "biometric_protected_key"
    val keyEntry = keyStore.getEntry(alias, null) as? KeyStore.SecretKeyEntry

    val secretKey = keyEntry?.secretKey

    fun getUserIv() {
        viewModelScope.launch {
            val result =  biometricLoginUseCase.getUserIv()
            userIv.value = result.getOrNull()!!
        }
    }

    fun initializeCipher(): Cipher {
        return encryptionManager.createDecryptionCipher(secretKey, userIv.value)
    }

    fun addAccount(cipher: Cipher) {
        viewModelScope.launch {
            val result = addAccountUseCase.addNewAccount(currentUserId, platformName, email, password, cipher)
        }
    }
}