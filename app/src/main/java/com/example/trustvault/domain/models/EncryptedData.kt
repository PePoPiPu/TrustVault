package com.example.trustvault.domain.models

data class EncryptedData(
    val cipherText: ByteArray?,
    val iv: ByteArray?
) {
    // Auto implemented function
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EncryptedData

        if (!cipherText.contentEquals(other.cipherText)) return false
        if (!iv.contentEquals(other.iv)) return false

        return true
    }
    // Auto implemented function
    override fun hashCode(): Int {
        var result = cipherText?.contentHashCode() ?: 0
        result = 31 * result + (iv?.contentHashCode() ?: 0)
        return result
    }
}
