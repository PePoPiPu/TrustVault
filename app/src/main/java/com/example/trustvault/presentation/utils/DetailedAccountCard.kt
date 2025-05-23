package com.example.trustvault.presentation.utils

import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.trustvault.R
import com.example.trustvault.presentation.models.AccountItem
import com.example.trustvault.presentation.screens.onboarding.GenericBiometricScreen
import com.example.trustvault.presentation.viewmodels.home.DetailedAccountCardViewModel

@Composable
fun DetailedAccountCard(
    onBackClick: () -> Unit,
    viewModel: DetailedAccountCardViewModel = hiltViewModel(),
    accountDetails: AccountItem
) {
    LaunchedEffect(Unit) {
        viewModel.getUserIv()
        viewModel.getUserMasterKey()
    }
    var decryptedPassword by remember { mutableStateOf("") }
    val userIv = viewModel.userIv.value
    val cipher = remember(userIv) {
        if(userIv != null && userIv.isNotEmpty()) {
            viewModel.initializeCipher()
        } else {
            null
        }
    }

    if(cipher != null) {
        GenericBiometricScreen(
            cryptoObject = BiometricPrompt.CryptoObject(cipher),
            onSuccess = {authorizedCipher ->
                decryptedPassword = viewModel.decryptPassword(authorizedCipher, accountDetails.password, accountDetails.salt, accountDetails.iv)
            },
            title = "Usa tu huella para ver la contraseña",
            subtitle = "Necesitamos tu huella para desencriptar la contraseña y poder enseñártela"
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp),
        colors = CardDefaults.cardColors(Color(0xFF1C1C2A)),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = if(accountDetails.iconResId != null) accountDetails.iconResId else R.drawable.question_mark),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(accountDetails.name, color = Color.White, fontWeight = FontWeight.Bold)
            Text(accountDetails.email, color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp, textAlign = TextAlign.Center)
            Text(decryptedPassword, color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp, textAlign = TextAlign.Center)
        }
    }
}