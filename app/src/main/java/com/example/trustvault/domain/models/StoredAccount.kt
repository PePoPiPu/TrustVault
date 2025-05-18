package com.example.trustvault.domain.models

class StoredAccount (
    val platformName: String,
    val storedEmail: String,
    val encryptedPassword: String,
    val salt: String,
    val encryptedIv: String
) {
    // constructor() : this ("", "", EncryptedData(ByteArray(0), ByteArray(0))) // Empty constructor to deserialize retrieved objects from Firestore in StoredAccountRepositoryImpl
    constructor() : this ("", "", "", "", "")
}