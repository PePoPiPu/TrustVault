package com.example.trustvault.presentation.viewmodels.home

import android.util.Base64
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustvault.data.encryption.EncryptionManager
import com.example.trustvault.domain.use_cases.BiometricLoginUseCase
import com.example.trustvault.presentation.screens.onboarding.UserPreferencesManager
import com.example.trustvault.presentation.utils.SecureCredentialStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.security.KeyStore
import javax.crypto.Cipher
import javax.inject.Inject

@HiltViewModel
class DetailedAccountCardViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val secureCredentialStore: SecureCredentialStore,
    private val biometricLoginUseCase: BiometricLoginUseCase,
    private val encryptionManager: EncryptionManager
): ViewModel() {
    val darkTheme = userPreferencesManager.getCurrentTheme()
    val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    val alias = "biometric_protected_key"
    val keyEntry = keyStore.getEntry(alias, null) as? KeyStore.SecretKeyEntry

    val secretKey = keyEntry?.secretKey
    var userIv: MutableState<ByteArray?> = mutableStateOf(ByteArray(0) { 0x00 })
    var masterKey = mutableStateOf("")

    fun initializeCipher(): Cipher {
        return encryptionManager.createDecryptionCipher(secretKey, userIv.value)
    }

    fun getUserIv() {
        viewModelScope.launch {
            val result =  biometricLoginUseCase.getUserIv()
            userIv.value = result.getOrNull()!!
        }
    }

    fun getUserMasterKey() {
        viewModelScope.launch {
            masterKey.value = secureCredentialStore.getMasterKey()
        }
    }

    fun decryptPassword(cipher: Cipher, password: String, salt: String, iv: String): String {
        // Derive key with same salt
        val decodedSalt = Base64.decode(salt, Base64.DEFAULT)
        val derivedKey = encryptionManager.deriveKeyFromMaster(masterKey.value, decodedSalt)
        val decodedPassword = Base64.decode(password, Base64.DEFAULT)
        val decodedIv = Base64.decode(iv, Base64.DEFAULT)
        val decryptedPassword = encryptionManager.decrypt(decodedPassword, derivedKey.derivedKey, decodedIv)
        Log.d("GODDAMN PASSWORD", decryptedPassword)
        return decryptedPassword
    }
}