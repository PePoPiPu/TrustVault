package com.example.trustvault.data.repositories

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.trustvault.domain.repositories.SMSRepository
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SMSRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : SMSRepository {
    override suspend fun verifyPhone(context: Context, phoneNumber: String): Result<Unit> {

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(30L, TimeUnit.SECONDS)
            .setActivity(context as Activity) // PUEDE CASCAR !!!
            .setCallbacks(phoneAuthProvider)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)

        return Result.success(Unit)
    }

    override suspend fun verifyCodeManually(context: Context, code: String): Result<Unit> {
        val verificationId = storedVerificationId
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


    var storedVerificationId: String? = null
}

    object phoneAuthProvider : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,

        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("TAG", "onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
            var storedVerificationId = verificationId
            var resendToken = token
        }

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d("TAG", "onVerificationCompleted:$credential")
            //signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("TAG", "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
            }

            // Show a message and update the UI
        }

        /*private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithCredential:success")

                        val user = task.result?.user
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w("TAG", "signInWithCredential:failure", task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                        // Update UI
                    }
                }
        }*/

    }