package com.example.trustvault.domain.models

data class User(
    val username: String,
    val email: String,
    val password: String,
    val phone: String,
    var encryptedKey: String?,
    var iv: String?
) {
    constructor() : this ("", "", "", "", null, null) // Empty constructor to de-serialize objects in Firebase
}
