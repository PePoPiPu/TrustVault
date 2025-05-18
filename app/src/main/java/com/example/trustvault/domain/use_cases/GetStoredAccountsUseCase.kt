package com.example.trustvault.domain.use_cases

import com.example.trustvault.domain.models.StoredAccount
import com.example.trustvault.domain.repositories.StoredAccountRepository
import javax.inject.Inject

class GetStoredAccountsUseCase @Inject constructor(private val storedAccountRepository: StoredAccountRepository){
    suspend fun getStoredAccounts(userId: String): Result<List<StoredAccount>> {
        return storedAccountRepository.getAccounts(userId)
    }
}