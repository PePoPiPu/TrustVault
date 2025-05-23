package com.example.trustvault.presentation.models

data class AccountItem(
    val iconResId: Int?,
    val name: String,
    val email: String,
    var password: String,
    var salt: String,
    val iv: String
)
