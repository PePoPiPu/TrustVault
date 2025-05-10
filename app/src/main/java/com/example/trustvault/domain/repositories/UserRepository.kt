package com.example.trustvault.domain.repositories

import com.example.trustvault.domain.models.User

interface UserRepository {
    suspend fun loginUser(username: String, password: String): Result<User>
    suspend fun registerUser(email: User): Result<Unit>
    suspend fun getUser(userId: String): Result<User>
    suspend fun updateUser(user: User): Result<Unit>
    suspend fun deleteUser(user: User): Result<Unit>
    suspend fun forgotPassword(email: String): Result<Unit>
}