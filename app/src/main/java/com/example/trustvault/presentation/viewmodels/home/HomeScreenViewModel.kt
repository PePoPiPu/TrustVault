package com.example.trustvault.presentation.viewmodels.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustvault.domain.models.StoredAccount
import com.example.trustvault.domain.use_cases.GetStoredAccountsUseCase
import com.example.trustvault.presentation.models.AccountItem
import com.example.trustvault.presentation.models.Platforms
import com.example.trustvault.presentation.screens.onboarding.UserPreferencesManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val getStoredAccountsUseCase: GetStoredAccountsUseCase,
) : ViewModel() {

    val darkTheme = userPreferencesManager.getCurrentTheme()
    // variables to be exposed in the presentation layer
    private val _getAccountsResult = MutableLiveData<List<StoredAccount>?>() // mutable and nullable version that can change inside the viewmodel
    private val _getAccountsError = MutableLiveData<String>()
    val getAccountsResult: LiveData<List<StoredAccount>?> = _getAccountsResult // Immutable version exposed to observer (UI), prevents bugs cause by logic in UI trying to modify the shared state
    val getAccountsError: LiveData<String> = _getAccountsError

    val currentUser = FirebaseAuth.getInstance().currentUser

    fun getAccounts() {
        viewModelScope.launch {
            val result = getStoredAccountsUseCase.getStoredAccounts(currentUser?.uid.toString())

            if(result.isSuccess) {
                val accountList = result.getOrNull()
                _getAccountsResult.value = accountList
            } else {
                val error = result.exceptionOrNull()
                _getAccountsError.value = error?.message
            }
        }
    }

    fun getAccountItems(accounts: List<StoredAccount>): List<AccountItem> {
        val accountItems = mutableListOf<AccountItem>()
        for (account in accounts) {
            val platform = enumValues<Platforms>().firstOrNull {it.name.equals(account.platformName, ignoreCase = true) }
            val accItem = AccountItem(
                iconResId = platform?.iconResId,
                name = account.platformName,
                email = account.storedEmail,
                password = account.encryptedPassword,
                salt = account.salt,
                iv = account.encryptedIv
            )
        accountItems.add(accItem)
        }
        return accountItems
    }
}