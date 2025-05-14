package com.example.trustvault.domain.repositories

import com.example.trustvault.domain.models.StoredAccount

interface StoredAccountRepository {
    suspend fun addAccount(userId: String, platformName: String, email: String, password: String): Result<Unit>
    suspend fun getAccounts(userId: String): Result<List<StoredAccount>> // Used mainly for getting all accounts to display in the presentation layer
    suspend fun getAccount(userId: String): Result<StoredAccount> // Used to get a specific account (might not implement)
    suspend fun updateAccount(userId: String): Result<Unit>
    suspend fun deleteAccount(userId: String): Result<Unit>
}