package com.example.trustvault.presentation.viewmodels.onboarding

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustvault.presentation.screens.onboarding.UserPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.inject.Inject

/**
 * ViewModel for the Get Started screen.
 *
 * This ViewModel handles the logic for initializing secure biometric keys in the Android KeyStore,
 * based on the selected authentication type, and manages the theme state via [UserPreferencesManager].
 *
 * It ensures that a secure encryption key exists before proceeding and exposes dark mode preference.
 */
@HiltViewModel
class GetStartedViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {

    private val _darkTheme = MutableLiveData<Boolean>()
    val darkTheme: LiveData<Boolean> get() = _darkTheme
    private val authType = userPreferencesManager.getCurrentAuthType()

    private val keyGenerator = KeyGenerator.getInstance(
        KeyProperties.KEY_ALGORITHM_AES,
        "AndroidKeyStore"
    )

    /**
     * Creates a [KeyGenParameterSpec] depending on the authentication type.
     *
     * @return Configured key generation parameters.
     */
    @RequiresApi(Build.VERSION_CODES.R)
    private fun currentKeyGenParameterSpec() : KeyGenParameterSpec {
        if(authType) {
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                "biometric_protected_key",
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            ).run {
                setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                setUserAuthenticationRequired(true) // requires biometric authentication
                setUserAuthenticationParameters(0, KeyProperties.AUTH_BIOMETRIC_STRONG or KeyProperties.AUTH_DEVICE_CREDENTIAL) // requires biometricAuthentication everytime the key is used
                build()
            }
            return keyGenParameterSpec
        } else {
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                "biometric_protected_key",
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            ).run {
                setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                setUserAuthenticationRequired(true) // requires biometric authentication
                setUserAuthenticationParameters(0, KeyProperties.AUTH_DEVICE_CREDENTIAL) // requires biometricAuthentication everytime the key is used
                build()
            }
            return keyGenParameterSpec
        }
    }

    /**
     * Generates a new secure key in the Android KeyStore using the current parameters.
     */
    @RequiresApi(Build.VERSION_CODES.R)
    private fun generateKey() {
        val keyGenParameterSpec = currentKeyGenParameterSpec()
        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    /**
     * Checks if the biometric-protected key already exists in the KeyStore.
     *
     * @return `true` if the key exists, `false` otherwise.
     */
    private fun keyExists() : Boolean {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        return keyStore.containsAlias("biometric_protected_key")
    }

//    fun deleteKey() {
//        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
//        if (keyStore.containsAlias("biometric_protected_key")) {
//            keyStore.deleteEntry("biometric_protected_key")
//        }
//    }

    /**
     * Checks for the existence of the secure key, and generates it if missing.
     *
     * Logs the current key if it already exists.
     */
    @RequiresApi(Build.VERSION_CODES.R)
    fun checkKey() {
        if(!keyExists()) {
            generateKey()
        } else {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            Log.d("CURRENT KEY", keyStore.getKey("biometric_protected_key", null).toString())
        }
    }

    init {
        // Launch a coroutine to collect from the Flow
        viewModelScope.launch {
            userPreferencesManager.darkThemeFlow.collect { isDarkMode ->
                _darkTheme.value = isDarkMode
            }
        }
    }
}