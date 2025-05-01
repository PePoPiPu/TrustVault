package com.example.trustvault.presentation.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.trustvault.R
import com.example.trustvault.presentation.theme.DarkColorScheme
import com.example.trustvault.presentation.theme.LightColorScheme
import com.example.trustvault.presentation.viewmodels.onboarding.RegisterViewModel

@Composable
fun WelcomeScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onContinueClick: () -> Unit = {}
) {
    val darkTheme = viewModel.darkTheme
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background) // Solid black base
    ) {
        Image(
            painter = if (darkTheme) painterResource(id = R.drawable.ellipse_278) else painterResource(id = R.drawable.ellipse_278_light),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .size(300.dp)
                .align(Alignment.BottomEnd)
                .alpha(0.7f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Logo
            Image(
                painter = painterResource(id = R.drawable.ic_trustvault),
                colorFilter = if (darkTheme) ColorFilter.tint(Color.White) else ColorFilter.tint(Color.Black),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Bienvenido a",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (darkTheme) DarkColorScheme.onSurface else LightColorScheme.onSurface
                )

                Row {
                    Text(
                        text = "Trust",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (darkTheme) DarkColorScheme.onSurface else LightColorScheme.onSurface
                    )
                    Text(
                        text = "Vault",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Normal,
                        color = if (darkTheme) DarkColorScheme.onSurface else LightColorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Empieza a cuidar de lo más importante: tu identidad digital.",
                    fontSize = 20.sp,
                    color = if (darkTheme) DarkColorScheme.onSurface else LightColorScheme.onSurface
                )
            }

            // Button with Arrow
            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .background(Color.Black, shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomEnd = 16.dp,
                        bottomStart = 0.dp
                    ))
                    .clickable { onContinueClick() }
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun TrustVaultWelcomeScreenPreview() {
    WelcomeScreen()
}
