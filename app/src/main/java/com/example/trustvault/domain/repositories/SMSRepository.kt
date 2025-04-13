package com.example.trustvault.domain.repositories

import android.content.Context

interface SMSRepository {
    suspend fun verifyPhone(context: Context, phoneNumber: String): Result<Unit>
    suspend fun verifyCodeManually(code: String, context: Context) : Result<Unit>
}