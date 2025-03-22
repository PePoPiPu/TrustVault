package com.example.trustvault.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LoginActivity {
    @Composable // Composable object
    @Preview
    fun LoginScreen() {
        Column (
            modifier = Modifier // Create this column and the attributes
                .fillMaxSize()
                .fillMaxHeight()// Make the layout fill all available space
                .background(Color(0xFF111111)),
            verticalArrangement = Arrangement.Center, // Vertical Alignment
            horizontalAlignment = Alignment.CenterHorizontally // Horizontal Alignment
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Bienvenido de nuevo",
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            listOf(Color(0xFFBC39DB), Color(0xFF7C8EFF), Color(0xFFE7A3F8))
                        ),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 200.dp, bottom = 32.dp)
                )
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                TextField(
                    value="",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth().padding(horizontal = 50.dp, vertical = 10.dp),
                    placeholder = {
                        Text(
                            text = "username"
                        )
                    }

                )
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                TextField(
                    value="",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth().padding(horizontal = 50.dp, vertical = 10.dp),
                    placeholder = {
                        Text(
                            text = "Password"
                        )
                    }

                )
            }

            Button(
                onClick = { /* Search user in database and log in */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 50.dp, vertical = 20.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF9C27B0), Color(0xFF673AB7))
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Entrar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "¿No tienes cuenta? Regístrate",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 250.dp, bottom = 16.dp)
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Recuperar contraseña",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
        }
    }

}