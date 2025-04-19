package com.example.trustvault.domain.use_cases

import com.example.trustvault.domain.models.User
import com.example.trustvault.domain.repositories.UserRepository
import javax.inject.Inject

class LogInUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(username : String, password : String): Result<User> {
        if(username.isEmpty() || password.isEmpty()) {
            return Result.failure(Exception("Complete all the fields"))
        }

        return userRepository.loginUser(username, password)
    }
}
