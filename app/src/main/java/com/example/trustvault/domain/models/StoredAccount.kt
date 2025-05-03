package com.example.trustvault.domain.models

class StoredAccount (
    val platformName: String,
    val storedEmail: String,
    val storedPassword: String
) {
    constructor() : this ("", "", "") // Empty constructor to deserialize retrieved objects from Firestore in StoredAccountRepositoryImpl
}