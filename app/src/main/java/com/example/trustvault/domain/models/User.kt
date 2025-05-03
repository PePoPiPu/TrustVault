package com.example.trustvault.domain.models

import android.accounts.Account

data class User(
    val username: String,
    val email: String,
    val password: String,
    val phone: String
) {
    constructor() : this ("", "", "", "") // Empty constructor to de-serialize objects in Firebase
}
