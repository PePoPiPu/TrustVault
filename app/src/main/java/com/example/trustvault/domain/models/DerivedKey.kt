package com.example.trustvault.domain.models

class DerivedKey(
    val derivedKey: ByteArray,
    val salt: ByteArray?
)