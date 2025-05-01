package com.example.trustvault.data.repositories

import com.example.trustvault.domain.models.StoredAccount
import com.example.trustvault.domain.repositories.StoredAccountRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StoredAccountRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) : StoredAccountRepository {
    override suspend fun addAccount(
        userId: String,
        accountName: String,
        accountEmail: String,
        accountPassword: String
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccounts(userId: String): Result<List<StoredAccount>> {
        return try {
            val snapshot = FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .collection("accounts")
                .get()
                .await()

            val accounts = snapshot.documents.mapNotNull { it.toObject(StoredAccount::class.java)!! }
            Result.success(accounts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAccount(userId: String): Result<StoredAccount> {
        TODO("Not yet implemented")
    }

    override suspend fun updateAccount(userId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount(userId: String): Result<Unit> {
        TODO("Not yet implemented")
    }


}