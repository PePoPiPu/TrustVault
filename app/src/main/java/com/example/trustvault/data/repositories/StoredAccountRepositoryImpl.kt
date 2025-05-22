package com.example.trustvault.data.repositories

import android.util.Base64
import android.util.Log
import com.example.trustvault.data.encryption.EncryptionManager
import com.example.trustvault.domain.models.StoredAccount
import com.example.trustvault.domain.models.User
import com.example.trustvault.domain.repositories.StoredAccountRepository
import com.example.trustvault.presentation.utils.SecureCredentialStore
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.crypto.Cipher
import javax.inject.Inject

class StoredAccountRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val encryptionManager: EncryptionManager,
    private val credentialStore: SecureCredentialStore
) : StoredAccountRepository {

    override suspend fun addAccount(
        userId: String,
        platformName: String,
        email: String,
        password: String,
        cipher: Cipher
    ): Result<Unit> {

        // retrieve master key for current user
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser?.uid
        val docSnapshot = firestore.collection("users")
            .document(userId.toString()) // to non-nullable string
            .get()
            .await()

        val user = docSnapshot.toObject(User::class.java)
        val encryptedMasterKey = Base64.decode(user?.encryptedKey, Base64.DEFAULT)
        val decryptedMasterKey = String(encryptionManager.decryptMasterKey(encryptedMasterKey, cipher), Charsets.UTF_8)
        credentialStore.saveMasterKey(decryptedMasterKey)
        // Create new derived key from master
        val derivedKey = encryptionManager.deriveKeyFromMaster(decryptedMasterKey, null)
        // encrypt password string with derived key
        val encryptedPasswordData = encryptionManager.encrypt(password, derivedKey.derivedKey)
        val derivedKeySalt = Base64.encodeToString(derivedKey.salt, Base64.DEFAULT)
        // encode to Base64 encrypted password + iv bytearrays
        val encryptedCipherText = Base64.encodeToString(encryptedPasswordData.cipherText, Base64.DEFAULT)
        val encryptedIv = Base64.encodeToString(encryptedPasswordData.iv, Base64.DEFAULT)
        // upload both the encoded and encrypted iv + ciphertext to firebase
        val newAccount = StoredAccount(
            platformName,
            email,
            encryptedCipherText,
            derivedKeySalt,
            encryptedIv
        )
        return try {
            if (currentUser != null) {
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