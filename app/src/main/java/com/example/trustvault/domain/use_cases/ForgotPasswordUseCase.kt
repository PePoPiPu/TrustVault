package com.example.trustvault.domain.use_cases

import com.example.trustvault.domain.repositories.UserRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(private val userRepository: UserRepository){
    suspend fun execute(email: String): Result<Unit> {
        if(email.isEmpty()) {
            return Result.failure(Exception("Email is blank"))
        }

        return userRepository.forgotPassword(email)
    }
}