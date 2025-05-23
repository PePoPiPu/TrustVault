package com.example.trustvault.domain.repositories

import com.example.trustvault.domain.models.User
import javax.crypto.Cipher
import javax.crypto.SecretKey

interface UserRepository {
    suspend fun loginUser(username: String, password: String): Result<User>
    suspend fun getUserIv(): Result<ByteArray>
    suspend fun loginBiometricUser(cipher: Cipher, secretKey: SecretKey?): Result<Unit>
    suspend fun registerUser(email: User, cipher: Cipher?): Result<Unit>
    suspend fun getUser(userId: String): Result<User>
    suspend fun logOut()
    suspend fun updateUser(user: User): Result<Unit>
    suspend fun deleteUser(user: User): Result<Unit>
    suspend fun forgotPassword(email: String): Result<Unit>
}