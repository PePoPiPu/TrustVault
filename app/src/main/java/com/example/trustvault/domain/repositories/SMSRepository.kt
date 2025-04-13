package com.example.trustvault.domain.repositories

interface SMSRepository {
    suspend fun verifyPhone(phoneNumber: String): Result<Unit>
}