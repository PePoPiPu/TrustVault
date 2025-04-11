package com.example.trustvault.domain.use_cases

import android.util.Log
import com.example.trustvault.domain.models.User
import com.example.trustvault.domain.repositories.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(user: User): Result<Unit> {
        if(user.email.isEmpty() || user.password.isEmpty() || user.phone.isEmpty()) {
            return Result.failure(Exception("User fields cannot be empty"))
        }

        return userRepository.registerUser(user)
    }
}