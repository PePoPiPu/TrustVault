package com.example.trustvault.domain.use_cases

import com.example.trustvault.domain.repositories.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun logOut() {
        userRepository.logOut()
    }
}