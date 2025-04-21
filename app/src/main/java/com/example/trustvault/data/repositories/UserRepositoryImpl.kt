package com.example.trustvault.data.repositories

import android.util.Log
import com.example.trustvault.domain.models.User
import com.example.trustvault.domain.repositories.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.lambdapioneer.argon2kt.Argon2Kt
import com.lambdapioneer.argon2kt.Argon2KtResult
import com.lambdapioneer.argon2kt.Argon2Mode
import de.mkammerer.argon2.Argon2Factory
import kotlinx.coroutines.tasks.await
import java.security.SecureRandom
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) : UserRepository {
    override suspend fun loginUser(
        username: String,
        password: String
    ): Result<User> {
        return try {
            val databaseUser = firestore.collection("users")
                .whereEqualTo("username", username)
                .get()
                .await()

            if (databaseUser.isEmpty) {
                Result.failure(Exception("User not found"))
            } else {
                val document = databaseUser.documents.first()
                val storedHash = document.getString("password") ?: ""

                if (verifyPassword(password, storedHash)) {

                    val user = document.toObject(User::class.java)
                    Result.success(user!!)
                } else {
                    Result.failure(Exception("Wrong password"))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun registerUser(
        user: User
    ): Result<Unit> {
        return try {
            val encodedArgon2String = hashPassword(user.password)

            val userWithHashedPassword = user.copy(password = encodedArgon2String)

            firestore.collection("users")
                .document()
                .set(userWithHashedPassword)
                .await()

            Log.d("Message: " , "Registering user!")

            Result.success(Unit)
        } catch (e: Exception) {
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
                Result.failure(Exception("User not found"))
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
                return Result.failure(Exception("User not found"))
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
                return Result.failure(Exception("User not found"))
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

    private fun hashPassword(password: String): String {
        val argon2Kt = Argon2Kt()
        // Since strings are immutable and cannot be wiped from memory in a secure manne
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