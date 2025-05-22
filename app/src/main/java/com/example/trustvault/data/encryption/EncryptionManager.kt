package com.example.trustvault.data.encryption

import com.example.trustvault.domain.models.DerivedKey
import com.example.trustvault.domain.models.EncryptedData
import com.lambdapioneer.argon2kt.Argon2Kt
import com.lambdapioneer.argon2kt.Argon2KtResult
import com.lambdapioneer.argon2kt.Argon2Mode
import java.nio.ByteBuffer
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Singleton
/**
 * Singleton object responsible for handling encryption-related operations such as
 * key derivation, encryption, and decryption of data using AES and Argon2.
 */
@Singleton
object EncryptionManager {
    private const val IV_LENGTH = 12
    private const val SALT_LENGTH = 16
    private const val AES_KEY_LENGTH = 32 // 256 bit
    private const val GCM_TAG_LENGTH = 128

    /**
     * Generates a cryptographic salt using a secure random number generator.
     *
     * @return A securely generated and wiped salt byte array.
     */
    private fun generateSalt() : ByteArray{
        val salt = ByteArray(SALT_LENGTH)
        SecureRandom().nextBytes(salt)

        // Wipe the salt variable in case of memory dumps
        salt.fill(0)

        return salt
    }

    /**
     * Derives a key from the provided master password using the Argon2id hashing algorithm.
     *
     * @param password The master password to derive the key from.
     * @param salt An optional salt. If null, a new salt is generated internally.
     * @return A [DerivedKey] object containing the derived encryption key and optionally the used salt.
     */
    fun deriveKeyFromMaster(password: String, salt: ByteArray?): DerivedKey {
        val argon2Kt = Argon2Kt()

        if(salt == null) {
            val generatedSalt = generateSalt()
            val hash : Argon2KtResult = argon2Kt.hash(
                mode = Argon2Mode.ARGON2_ID,
                password = password.toByteArray(),
                salt = generatedSalt,
                tCostInIterations = 6,
                mCostInKibibyte = 65526
            )

            val byteBuffer: ByteBuffer = hash.rawHash
            // Creates a new byteArray with size = to remaining bytes to be read in the bytebuffer
            val byteArray = ByteArray(byteBuffer.remaining())
            // Transfer bytes from the buffer to destination byteArray
            byteBuffer.get(byteArray)
            // Sliced sub-array return to ensure we get only the first 32 bytes (argon2 hash might be longer)
            val derivedKey = DerivedKey(
                derivedKey = byteArray.sliceArray(0 until 32), // 256 bit encryption key
                salt = generatedSalt
            )
            return derivedKey
        } else {
            val hash : Argon2KtResult = argon2Kt.hash(
                mode = Argon2Mode.ARGON2_ID,
                password = password.toByteArray(),
                salt = salt,
                tCostInIterations = 6,
                mCostInKibibyte = 65526
            )

            val byteBuffer: ByteBuffer = hash.rawHash
            // Creates a new byteArray with size = to remaining bytes to be read in the bytebuffer
            val byteArray = ByteArray(byteBuffer.remaining())
            // Transfer bytes from the buffer to destination byteArray
            byteBuffer.get(byteArray)
            // Sliced sub-array return to ensure we get only the first 32 bytes (argon2 hash might be longer)
            val derivedKey = DerivedKey(
                derivedKey = byteArray.sliceArray(0 until 32), // 256 bit encryption key
                salt = null
            )
            return derivedKey
        }
    }

    /**
     * Encrypts a plaintext password using the provided AES key with CBC mode and PKCS5 padding.
     *
     * @param plaintextPassword The password to encrypt.
     * @param key The AES key used for encryption.
     * @return An [EncryptedData] object containing the encrypted bytes and the IV used.
     */
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

    /**
     * Creates an AES encryption cipher instance using the provided secret key.
     *
     * @param key The secret key used to initialize the cipher.
     * @return The initialized AES [Cipher] instance in encryption mode.
     */
    fun createEncryptionCipher(key: SecretKey?): Cipher {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "AndroidKeyStoreBCWorkaround")

        cipher.init(Cipher.ENCRYPT_MODE, key)
        return cipher
    }

    /**
     * Creates an AES decryption cipher instance using the provided secret key and IV.
     *
     * @param key The secret key used for decryption.
     * @param iv The initialization vector used during encryption.
     * @return The initialized AES [Cipher] instance in decryption mode.
     */
    fun createDecryptionCipher(key: SecretKey?, iv: ByteArray?): Cipher {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "AndroidKeyStoreBCWorkaround")
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)
        return cipher
    }

    /**
     * Encrypts the provided master key using the given cipher instance.
     *
     * @param masterKey The master key to encrypt.
     * @param cipher The initialized cipher to use for encryption.
     * @return An [EncryptedData] object containing the encrypted master key and IV.
     */
    fun encryptMasterKey(masterKey: ByteArray, cipher: Cipher?): EncryptedData {
        val iv = cipher?.iv
        val encryptedBytes = cipher?.doFinal(masterKey)

        return EncryptedData(cipherText = encryptedBytes, iv = iv)
    }

    /**
     * Decrypts the encrypted master key using the provided cipher.
     *
     * @param masterKey The encrypted master key bytes.
     * @param cipher The cipher used to decrypt the master key.
     * @return The decrypted master key as a byte array.
     */
    fun decryptMasterKey(masterKey: ByteArray, cipher: Cipher): ByteArray {
        return cipher.doFinal(masterKey)
    }

    /**
     * Decrypts the given encrypted data using AES CBC mode with the specified key and IV.
     *
     * @param encryptedData The encrypted byte array to decrypt.
     * @param key The AES key used for decryption.
     * @param iv The initialization vector used during encryption.
     * @return The decrypted string in UTF-8 encoding.
     */
    fun decrypt(encryptedData: ByteArray, key: ByteArray, iv: ByteArray): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val secretKey = SecretKeySpec(key, "AES")
        val ivSpec = IvParameterSpec(iv)

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)

        val decryptedBytes = cipher.doFinal(encryptedData)
        return String(decryptedBytes, Charsets.UTF_8)
    }
}