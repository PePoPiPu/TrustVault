package com.example.trustvault.domain.use_cases

import com.example.trustvault.domain.models.StoredAccount
import com.example.trustvault.domain.repositories.StoredAccountRepository
import javax.inject.Inject

class AddAccountUseCase @Inject constructor(private val storedAccountRepository: StoredAccountRepository) {
    suspend fun addNewAccount(userId: String, newAccount: StoredAccount) : Result<Unit> {
        return storedAccountRepository.addAccount(userId, newAccount)
    }
}