package com.example.trustvault.domain.repositories

import android.content.Context

interface SMSRepository {
    suspend fun sendCode(context: Context, phoneNumber: String): Result<String>
    suspend fun verifyCodeManually(verificationId: String, code: String, context: Context) : Result<Unit>
}