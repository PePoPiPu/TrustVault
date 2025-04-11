package com.example.trustvault.domain.models

data class User(
    val id: String?,
    val username: String,
    val email: String,
    val password: String,
    val phone: String
)
