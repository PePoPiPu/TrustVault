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

    var storedVerificationId: String? = null

    override suspend fun verifyCodeManually(code: String, context: Context): Result<Unit> {
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

}


// FOR AUTO RETRIEVAL
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


    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
        TODO("Not yet implemented")
    }

    override fun onVerificationFailed(p0: FirebaseException) {
        TODO("Not yet implemented")
    }
}