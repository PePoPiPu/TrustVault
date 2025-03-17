package com.example.trustvault

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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LoginActivity {

    @Composable // Allows us to create a Composable object
    @Preview
    fun LoginScreen() {
        Column (
            modifier = Modifier // Defining the attributes for this column
                .fillMaxSize() // Make the layout fill all available space
                .background(Color(0xFF111111)),
            verticalArrangement = Arrangement.Center, // Vertical Alignment
            horizontalAlignment = Alignment.CenterHorizontally // Horizontal Alignment
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.ic_trustvault), // Paints the resource
                contentDescription = "TrustVault Logo", // Content Description for impaired individuals
                modifier = Modifier.size(100.dp)
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Trust",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 32.dp)
                )
                Text(
                    text = "Vault",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 32.dp)
                )
            }

            // Login Button
            OutlinedButton(
                onClick = { /* TODO: Handle Login */},
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Iniciar sesión", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp)) // Gives space between elements

            // Register Button + Gradient
            Button(
                onClick = {/* TODO: Handle registration */},
                modifier = Modifier
                    .fillMaxWidth(0.8f) // resulting MaxWidth = MaxWidth * fraction AKA reduce max space
                    .height(50.dp), // Height of the button
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Transparent as it is the same color of the background
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFFBC39DB), Color(0xFF7C8EFF), Color(0xFFE7A3F8))
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Regístrate", color = Color.White, fontSize = 16.sp)
                }
            }

        }
    }
}