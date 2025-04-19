package com.example.trustvault.presentation.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.example.trustvault.presentation.theme.LightColorScheme
import com.example.trustvault.presentation.viewmodels.RegisterViewModel

@Composable
fun OnboardingStep2(
    viewModel: RegisterViewModel = hiltViewModel(),
    onContinueClick: () -> Unit = {}
) {

    val darkTheme = viewModel.darkTheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background)
    ) {
        // Gradient overlay at top
        Image(
            painter = if (darkTheme) painterResource(id = R.drawable.ellipse_gradient) else painterResource(id = R.drawable.ellipse_278_light),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .size(250.dp)
                .align(Alignment.TopCenter)
                .rotate(180f)
                .alpha(0.7f)
        )

        Image(
            painter = if (darkTheme) painterResource(id = R.drawable.ellipse_gradient) else painterResource(id = R.drawable.ellipse_278_light),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .size(250.dp)
                .align(Alignment.BottomCenter)
                .alpha(0.7f)
        )

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            val compositionDark by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.on_boarding_2_dark))
            val compositionLight by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.on_boarding_2_light))
            val progress by animateLottieCompositionAsState(
                compositionDark,
                iterations = LottieConstants.IterateForever
            )

            LottieAnimation(
                composition = if (darkTheme) compositionDark else compositionLight,
                progress = { progress },
                modifier = Modifier
                    .size(250.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Texts
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Vigilancia en la Dark Web",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (darkTheme) Color.White else Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Si tu contraseña se filtra, lo sabrás al instante.",
                    fontSize = 16.sp,
                    color = if (darkTheme) Color.White.copy(alpha = 0.85f) else Color.Black.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center
                )
            }

            // Bottom controls
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Page indicator
                Row (
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    PageDot(false, darkTheme)
                    PageDot(true, darkTheme)
                    PageDot(false, darkTheme)
                }

                // Continue Button
                Button(
                    onClick = {
                        onContinueClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Transparent as it is the same color of the background
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Color(0xFF121515),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Continuar", color = Color.White, fontSize = 16.sp)
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}