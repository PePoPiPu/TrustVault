package com.example.trustvault.domain.use_cases

import android.content.Context
import com.example.trustvault.domain.repositories.SMSRepository
import javax.inject.Inject

class SMSAuthUseCase @Inject constructor(private val smsRepository: SMSRepository){
    suspend fun execute(context: Context, phoneNumber: String): Result<Unit> {
        return smsRepository.verifyPhone(context, phoneNumber)
    }

    suspend fun executeVerification(context: Context, code: String) : Result<Unit> {
        return smsRepository.verifyCodeManually(context, code, )
    }
}