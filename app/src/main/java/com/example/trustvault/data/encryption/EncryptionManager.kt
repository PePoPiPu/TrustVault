package com.example.trustvault.data.encryption

import com.example.trustvault.domain.models.EncryptedData
import com.lambdapioneer.argon2kt.Argon2Kt
import com.lambdapioneer.argon2kt.Argon2KtResult
import com.lambdapioneer.argon2kt.Argon2Mode
import java.nio.ByteBuffer
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionManager {
    private const val IV_LENGTH = 12
    private const val SALT_LENGTH = 16
    private const val AES_KEY_LENGTH = 32 // 256 bit
    private const val GCM_TAG_LENGTH = 128

    private fun generateSalt() : ByteArray{
        val salt = ByteArray(SALT_LENGTH)
        SecureRandom().nextBytes(salt)

        // Wipe the salt variable in case of memory dumps
        salt.fill(0)

        return salt
    }

    // Derives an encryption key from the master password.
    fun deriveKeyFromMaster(password: String, salt: ByteArray): ByteArray {
        val argon2Kt = Argon2Kt()

        val hash : Argon2KtResult = argon2Kt.hash(
            mode = Argon2Mode.ARGON2_ID,
            password = password.toByteArray(),
            salt = generateSalt(),
            tCostInIterations = 6,
            mCostInKibibyte = 65526
        )

        val byteBuffer: ByteBuffer = hash.rawHash
        // Creates a new byteArray with size = to remaining bytes to be read in the bytebuffer
        val byteArray = ByteArray(byteBuffer.remaining())
        // Transfer bytes from the buffer to destination byteArray
        byteBuffer.get(byteArray)
        // Sliced sub-array return to ensure we get only the first 32 bytes (argon2 hash might be longer)
        return byteArray.sliceArray(0 until 32)
    }

    fun encrypt (plaintextPassword: String, key: ByteArray) : EncryptedData {
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)

        // Perform chain block XOR operations taking a random IV to encryption patterns
        // PKCS5Padding fills the last block of plaintext so the size matches the cipher's block size
        // "hello" = 5 bytes, PKCS5Padding fills 11 bytes to reach 16 bytes (cipher's block size)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val secretKey = SecretKeySpec(key, "AES")
        val ivSpec = IvParameterSpec(iv)

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)

        val encryptedBytes = cipher.doFinal(plaintextPassword.toByteArray(Charsets.UTF_8))

        return EncryptedData(cipherText = encryptedBytes, iv = iv)
    }

    fun decrypt(encryptedData: ByteArray, key: ByteArray, iv: ByteArray): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val secretKey = SecretKeySpec(key, "AES")
        val ivSpec = IvParameterSpec(iv)

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)

        val decryptedBytes = cipher.doFinal(encryptedData)
        return String(decryptedBytes, Charsets.UTF_8)
    }
}