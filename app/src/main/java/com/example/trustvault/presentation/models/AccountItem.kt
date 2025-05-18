package com.example.trustvault.presentation.models

import androidx.compose.ui.graphics.painter.Painter

data class AccountItem(
    val iconResId: Int,
    val name: String,
    val email: String,
    var password: String,
    var salt: String,
    val iv: String
)
