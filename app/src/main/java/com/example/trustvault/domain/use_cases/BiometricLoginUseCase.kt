package com.example.trustvault.domain.use_cases

import com.example.trustvault.domain.repositories.UserRepository
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.inject.Inject

class BiometricLoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun executeBiometricLogin (cipher: Cipher, secretKey: SecretKey?): Result<Unit> {
        return userRepository.loginBiometricUser(cipher, secretKey)
    }
    suspend fun getUserIv (): Result<ByteArray> {
        return userRepository.getUserIv()
    }
}