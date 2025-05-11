package com.example.trustvault.domain.models

data class EncryptedData(
    val cipherText: ByteArray,
    val iv: ByteArray
)
