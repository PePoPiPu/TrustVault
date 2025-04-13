package com.example.trustvault.domain.use_cases

import com.example.trustvault.domain.repositories.SMSRepository
import javax.inject.Inject

class SMSAuthUseCase @Inject constructor(private val smsRepository: SMSRepository){
    suspend fun execute(phoneNumber: String): Result<Unit> {
        return smsRepository.verifyPhone(phoneNumber)
    }
}