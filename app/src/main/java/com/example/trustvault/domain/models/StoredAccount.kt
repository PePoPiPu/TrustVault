package com.example.trustvault.domain.models

class StoredAccount (
    val platformName: String,
    val storedEmail: String,
    // val storedPassword: EncryptedData
    val storedPassword: String
) {
    // constructor() : this ("", "", EncryptedData(ByteArray(0), ByteArray(0))) // Empty constructor to deserialize retrieved objects from Firestore in StoredAccountRepositoryImpl
    constructor() : this ("", "", "")
}