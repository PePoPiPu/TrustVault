package com.example.trustvault.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.trustvault.R
import com.example.trustvault.presentation.theme.DarkColorScheme
import com.example.trustvault.presentation.theme.DarkModePrimaryGradient
import com.example.trustvault.presentation.theme.LightColorScheme
import com.example.trustvault.presentation.theme.LightModePrimaryGradient
import com.example.trustvault.presentation.viewmodels.home.HomeScreenViewModel
import kotlinx.coroutines.delay

@Composable
fun DarkWebMonitor(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val darkTheme = viewModel.darkTheme
    var getAccountResult = viewModel.getAccountsResult.value
    var isScanning by remember { mutableStateOf(false) }
    var progressBar by remember { mutableFloatStateOf(0f) }

    if (isScanning && progressBar < 1f) {
        LaunchedEffect(Unit) {
            val duration = 5000L
            val steps = 100
            val delayPerStep = duration / steps
            repeat(steps) {
                delay(delayPerStep)
                progressBar += 1f / steps
            }
            progressBar = 1f
        }
    }

    Column(
        modifier = Modifier // Create this column and the attributes
            .fillMaxSize()
            .fillMaxHeight()// Make the layout fill all available space
            .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background),
        verticalArrangement = Arrangement.Center, // Vertical Alignment
        horizontalAlignment = Alignment.CenterHorizontally // Horizontal Alignment
    ) {
        val progressBarGradient = if (darkTheme) DarkModePrimaryGradient else LightModePrimaryGradient
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.dark_web_scanner_anim))
        val previewComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.dark_web_scanner_preview_anim))
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever
        )

        if (isScanning) {

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .size(250.dp)
                    .scale(1.5f)
            )

            Spacer(modifier = Modifier.height(100.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally)
             {
                 Box(
                     modifier = Modifier
                         .fillMaxWidth(0.7f)
                         .height(6.dp)
                         .background(color = Color(0xFF292738), shape = RoundedCornerShape(50))
                 ) {
                     Box(
                         modifier = Modifier
                             .fillMaxHeight()
                             .fillMaxWidth(progress.coerceIn(0f, 1f))
                             .background(
                                 brush = progressBarGradient,
                                 shape = RoundedCornerShape(50)
                             )
                     )
                 }
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Analizando vulnerabilidades de contraseñas...${(progress * 100).toInt()}%",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (darkTheme) Color.White else Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LottieAnimation(
                composition = previewComposition,
                progress = { progress },
                modifier = Modifier
                    .size(250.dp)
                    .scale(1.5f)
            )

            Spacer(modifier = Modifier.height(100.dp))


            Text(
                text = "¿Quieres saber si alguna de tus credenciales está en peligo?",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (darkTheme) Color.White else Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Deja que TrustVault te ayude",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (darkTheme) Color.White else Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {isScanning = true},
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Transparent as it is the same color of the background
                contentPadding = PaddingValues(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            if (darkTheme) DarkModePrimaryGradient else LightModePrimaryGradient,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Escanear", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}

