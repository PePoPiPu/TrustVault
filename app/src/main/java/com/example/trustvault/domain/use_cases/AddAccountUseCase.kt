package com.example.trustvault.domain.use_cases

import com.example.trustvault.domain.models.StoredAccount
import com.example.trustvault.domain.repositories.StoredAccountRepository
import javax.crypto.Cipher
import javax.inject.Inject

class AddAccountUseCase @Inject constructor(private val storedAccountRepository: StoredAccountRepository) {
    suspend fun addNewAccount(userId: String, platformName: String, email: String, password: String, cipher: Cipher) : Result<Unit> {
        return storedAccountRepository.addAccount(userId, platformName, email, password, cipher)
    }
}