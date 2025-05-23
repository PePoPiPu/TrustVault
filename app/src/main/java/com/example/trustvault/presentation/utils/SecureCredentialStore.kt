package com.example.trustvault.presentation.utils

import android.app.Application
import android.content.Context
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "encrypted_user_data")

@Singleton
class SecureCredentialStore @Inject constructor(application: Application) {
    private val context: Context = application.applicationContext
    private val IV_KEY = stringPreferencesKey("iv")
    private val EMAIL_KEY = stringPreferencesKey("email")
    private val ENCRYPTED_PASS_KEY = stringPreferencesKey("encryptedPassword")
    private val MASTER_KEY = stringPreferencesKey("masterKey")


    val ivFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[IV_KEY] ?: ""
    }

    val emailFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[EMAIL_KEY] ?: ""
    }

    val encryptedPassFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[ENCRYPTED_PASS_KEY] ?: ""
    }

    val masterKeyFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[MASTER_KEY] ?: ""
    }

    suspend fun saveIv(iv: ByteArray?) {
        context.dataStore.edit { preferences -> preferences[IV_KEY] = iv?.toBase64().toString() }
    }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit { preferences -> preferences[EMAIL_KEY] = email }
    }

    suspend fun saveEncryptedPassword(encryptedPassword: ByteArray?) {
       context.dataStore.edit { preferences -> preferences[ENCRYPTED_PASS_KEY] =
           encryptedPassword?.toBase64().toString()
       }
    }

    suspend fun saveMasterKey(masterKey: String) {
        context.dataStore.edit { preferences -> preferences[MASTER_KEY] = masterKey }
    }

    suspend fun deleteMasterKey(masterKey: String) {
        context.dataStore.edit { preferences -> preferences[MASTER_KEY] = "" }
    }

    suspend fun getIv(): String = ivFlow.first()
    suspend fun getEmail(): String = emailFlow.first()
    suspend fun getEncryptedPassword(): String = encryptedPassFlow.first()
    suspend fun getMasterKey(): String = masterKeyFlow.first()

    // Saving a ByteArray as a base64 string allows for safe saving (safe as in no data corruption)
    fun ByteArray.toBase64(): String = Base64.encodeToString(this, Base64.DEFAULT)
}