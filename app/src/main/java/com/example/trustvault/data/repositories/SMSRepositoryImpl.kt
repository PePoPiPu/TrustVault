package com.example.trustvault.data.repositories

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.trustvault.domain.repositories.SMSRepository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SMSRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : SMSRepository {

    var storedVerificationId: String? = null

    // Sends Verification code
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun sendCode(context: Context, phoneNumber: String): Result<String> {
        return suspendCancellableCoroutine { continuation ->
            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(30L, TimeUnit.SECONDS)
                .setActivity(context as Activity)  // Ensure the context is an Activity
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        Log.d("TAG", "onCodeSent: $verificationId")
                        Toast.makeText(context, "¡Código enviado!", Toast.LENGTH_LONG).show()
                        continuation.resume(Result.success(verificationId), null)
                    }

                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                        Log.d("TAG", "Verification completed automatically.")
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        Log.e("TAG", "Verification failed: ${e.message}")
                        continuation.resume(Result.failure(e), null)
                    }
                })
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)  // Start the verification process
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun verifyCodeManually(verificationId: String, code: String, context: Context): Result<Unit> {
        Log.d("VERIFICATIONID", verificationId)
        return if (verificationId != null) {
            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            suspendCancellableCoroutine { continuation ->
                FirebaseAuth.getInstance()
                    .signInWithCredential(credential)
                    .addOnCompleteListener(context as Activity) { task ->
                        if (task.isSuccessful) {
                            Log.d("TAG", "signInWithCredential:success")
                            continuation.resume(Result.success(Unit), null)
                        } else {
                            Log.w("TAG", "signInWithCredential:failure", task.exception)
                            continuation.resume(
                                Result.failure(task.exception ?: Exception("Unknown error")),
                                null
                            )
                        }
                    }
            }
        } else {
            Result.failure(Exception("Verification ID is null"))
        }
    }
}
