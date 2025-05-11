package com.example.trustvault.data.repositories

import android.util.Log
import com.example.trustvault.domain.models.StoredAccount
import com.example.trustvault.domain.repositories.StoredAccountRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StoredAccountRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) : StoredAccountRepository {
    override suspend fun addAccount(
        userId: String,
        newAccount: StoredAccount
    ): Result<Unit> {
        return try {
            if (FirebaseAuth.getInstance().currentUser?.uid != null) {
                val snapshot = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(userId)
                    .collection("accounts")
                    .add(newAccount)
                    .await()
                Result.success(Unit)
            } else {
                throw Exception("User is not logged in")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAccounts(userId: String): Result<List<StoredAccount>> {
        return try {
            Log.d("CurrentUserStoredAccountsRepo", FirebaseAuth.getInstance().currentUser?.uid.toString())
            if (FirebaseAuth.getInstance().currentUser?.uid != null) {
                val snapshot = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(userId)
                    .collection("accounts")
                    .get()
                    .await()

                val accounts = snapshot.documents.mapNotNull { it.toObject(StoredAccount::class.java)!! }
                Log.d("Accounts", accounts.first().platformName.toString())
                Result.success(accounts)
            } else {
                throw Exception("User is not logged in")
            }
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