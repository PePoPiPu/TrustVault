package com.example.trustvault.domain.use_cases

import android.content.Context
import com.example.trustvault.domain.repositories.SMSRepository
import javax.inject.Inject

class SMSAuthUseCase @Inject constructor(private val smsRepository: SMSRepository){
    suspend fun executeCodeSend(context: Context, phoneNumber: String): Result<String> {
        return smsRepository.sendCode(context, phoneNumber)
    }

    suspend fun executeVerification(verificationId: String, code: String, context: Context): Result<Unit> {
        return smsRepository.verifyCodeManually(verificationId, code, context)
    }
}