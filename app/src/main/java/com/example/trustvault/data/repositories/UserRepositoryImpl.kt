package com.example.trustvault.data.repositories

import android.util.Base64
import android.util.Log
import com.example.trustvault.data.encryption.EncryptionManager
import com.example.trustvault.domain.exceptions.UserNotFoundException
import com.example.trustvault.domain.models.User
import com.example.trustvault.domain.repositories.UserRepository
import com.example.trustvault.presentation.utils.SecureCredentialStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lambdapioneer.argon2kt.Argon2Kt
import com.lambdapioneer.argon2kt.Argon2KtResult
import com.lambdapioneer.argon2kt.Argon2Mode
import de.mkammerer.argon2.Argon2Factory
import kotlinx.coroutines.tasks.await
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val credentialStore: SecureCredentialStore,
    private val encryptionManager: EncryptionManager
    ) : UserRepository {

    val auth = FirebaseAuth.getInstance()

    override suspend fun loginUser(
        email: String,
        password: String
    ): Result<User> {
        return try {
            if (auth.currentUser != null) {
                auth.signOut()
            }

            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: throw Exception("User not found after login")
//            Log.d("AuthResultUser", authResult.user?.uid.toString())
//            Log.d("CurrentLoggedUser", auth.currentUser?.uid.toString())
            val userDoc = firestore.collection("users")
                .document(firebaseUser.uid)
                .get()
                .await()

            if (!userDoc.exists()) {
                Result.failure(UserNotFoundException())
            } else {
                val user = userDoc.toObject(User::class.java)
                Result.success(user!!)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getUserIv(): Result<ByteArray> {
        return try {
            // Get credentials from dataStore
            val userEmail = credentialStore.getEmail()

            // Get user that has current stored email
            val querySnapshot = firestore.collection("users")
                .whereEqualTo("email", userEmail)
                .get()
                .await()

            if(!querySnapshot.isEmpty) {
                val user = querySnapshot.documents[0].toObject(User::class.java)

                val encodedIv = user?.iv
                val decodedIv = Base64.decode(encodedIv, Base64.DEFAULT)

                return Result.success(decodedIv)
            } else {
                return Result.failure(UserNotFoundException())
            }

        } catch (e: Exception) {
            Log.d("BIOMETRIC LOGIN ERROR: ", e.toString())
            return Result.failure(e)
        }
    }

    override suspend fun loginBiometricUser(cipher: Cipher, secretKey: SecretKey?): Result<Unit> {
        return try {
            // Get credentials from dataStore
            val userEmail = credentialStore.getEmail()
            val encodedUserIv = credentialStore.getIv()
            val encryptedUserPassword = credentialStore.getEncryptedPassword()

            // Get user that has current stored email
            val querySnapshot = firestore.collection("users")
                .whereEqualTo("email", userEmail)
                .get()
                .await()

            if(!querySnapshot.isEmpty) {
                val user = querySnapshot.documents[0].toObject(User::class.java)

                val encodedMasterKey = user?.encryptedKey
                val decodedMasterKey = Base64.decode(encodedMasterKey, Base64.DEFAULT)
                val masterKey = encryptionManager.decryptMasterKey(decodedMasterKey, cipher)
                val decodedUserIv = Base64.decode(encodedUserIv, Base64.DEFAULT)
                val decodedEncryptedUserPassword = Base64.decode(encryptedUserPassword, Base64.DEFAULT)
                val userPassword = encryptionManager.decrypt(decodedEncryptedUserPassword, masterKey, decodedUserIv)

                loginUser(userEmail, userPassword)

                return Result.success(Unit)
            } else {
                return Result.failure(UserNotFoundException())
            }

        } catch (e: Exception) {
            Log.d("BIOMETRIC LOGIN ERROR: ", e.toString())
            return Result.failure(e)
        }
    }

    override suspend fun registerUser(
        user: User,
        cipher: Cipher?
    ): Result<Unit> {
        val db = FirebaseFirestore.getInstance()

        return try {

            // Create master key by deriving user plain text password through a KDF (Key Derivation Function)
            val masterKey = EncryptionManager.deriveKeyFromMaster(user.password, null) // Returns a 256 bit array as an AES encryption key

            // Encrypt password with master key and save email + password + IV
            val encryptedData = EncryptionManager.encrypt(user.password, masterKey.derivedKey)

            credentialStore.saveIv(encryptedData.iv)
            credentialStore.saveEmail(user.email)
            credentialStore.saveEncryptedPassword(encryptedData.cipherText)

            // Encrypt master key with device key from KeyStore
            val encryptedMasterKeyData = EncryptionManager.encryptMasterKey(masterKey.derivedKey, cipher)

            val masterKeyBase64 = android.util.Base64.encodeToString(encryptedMasterKeyData.cipherText, android.util.Base64.DEFAULT)
            val ivBase64 = android.util.Base64.encodeToString(encryptedMasterKeyData.iv, android.util.Base64.DEFAULT)
            // Hash password
            val encodedArgon2String = hashPassword(user.password)

            var userWithHashedPassword = user.copy(password = encodedArgon2String)

            userWithHashedPassword.encryptedKey = masterKeyBase64
            userWithHashedPassword.iv = ivBase64
            // Register the user first in Firebase Auth
            val authResult = auth.createUserWithEmailAndPassword(user.email, user.password).await()
            // Check if the userId exists
            val userId = authResult.user?.uid ?: throw Exception("User ID not found after registration")

            db.collection("users")
                .document(userId)
                .set(userWithHashedPassword)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("ERROR: ", e.toString())
            Result.failure(e)
        }
    }

    override suspend fun getUser(username: String): Result<User> {
        return try {
            val querySnapshot = firestore.collection("users")
                .whereEqualTo("username", username)
                .get()
                .await()

            if(!querySnapshot.isEmpty) {
                val user = querySnapshot.documents[0].toObject(User::class.java)
                Result.success(user!!)
            } else {
                Result.failure(UserNotFoundException())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(user: User): Result<Unit> {
        return try {
            val querySnapshot = firestore.collection("users")
                .whereEqualTo("username", user.username)
                .get()
                .await()

            if(querySnapshot.isEmpty) {
                return Result.failure(UserNotFoundException())
            }

            val documentSnapshot = querySnapshot.documents[0]

            val hashedPassword = hashPassword(user.password)


            firestore.collection("users")
                .document(documentSnapshot.id)
                .update(
                    "username", user.username,
                    "email", user.email,
                    "phone", user.phone,
                    "password", hashedPassword
                )
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteUser(user: User): Result<Unit> {
        return try {
            val querySnapshot = firestore.collection("users")
                .whereEqualTo("username", user.username)
                .get()
                .await()

            if (querySnapshot.isEmpty) {
                return Result.failure(UserNotFoundException())
            }

            val documentSnapshot = querySnapshot.documents[0]

            firestore.collection("users")
                .document(documentSnapshot.id)
                .delete()
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun forgotPassword(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun hashPassword(password: String): String {
        val argon2Kt = Argon2Kt()
        // Since strings are immutable and cannot be wiped from memory in a secure manner
        // we have to transform the string into a byteArray to prevent memory dumps attack and have
        // the string lying around garbage memory
        val passwordByteArray = password.toByteArray(Charsets.UTF_8)

        val hashResult : Argon2KtResult = argon2Kt.hash (
            mode = Argon2Mode.ARGON2_ID, // Mode recommended by OWASP
            password = passwordByteArray,
            salt = generateSalt(),
            tCostInIterations = 6,
            mCostInKibibyte = 65526
        )

        val encodedHashResult = hashResult.encodedOutputAsString() // Example output $argon2i$v=19$m=65536,t=5,p=1$<salt>$<hash>

        // Wipe the password variable in case of memory dumps
        passwordByteArray.fill(0)

        return encodedHashResult
    }

    private fun generateSalt(length: Int = 32) : ByteArray{
        val salt = ByteArray(length)
        SecureRandom().nextBytes(salt)

        // Wipe the salt variable in case of memory dumps
        salt.fill(0)

        return salt
    }
    private fun verifyPassword(inputPassword: String, storedEncodedHash: String): Boolean {
        val argon2 = Argon2Factory.create()

        return try {
            argon2.verify(storedEncodedHash, inputPassword.toCharArray())
        } finally {
            argon2.wipeArray(inputPassword.toCharArray())
        }
    }
}