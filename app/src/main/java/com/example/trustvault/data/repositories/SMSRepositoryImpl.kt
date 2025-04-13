package com.example.trustvault.data.repositories

import androidx.compose.ui.platform.LocalContext
import com.example.trustvault.domain.repositories.SMSRepository
import com.example.trustvault.presentation.screens.onboarding.SMSAuthScreen
import com.example.trustvault.presentation.viewmodels.SMSAuthScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SMSRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) : SMSRepository {
    override suspend fun verifyPhone(phoneNumber: String): Result<Unit> {


        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(30L, TimeUnit.SECONDS)
            .setActivity(this)
    }
}