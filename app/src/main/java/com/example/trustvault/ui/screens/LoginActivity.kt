package com.example.trustvault.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trustvault.R
import com.example.trustvault.ui.theme.DarkColorScheme
import com.example.trustvault.ui.theme.DarkModePrimaryGradient
import com.example.trustvault.ui.theme.DisabledButtonGradient
import com.example.trustvault.ui.theme.LightColorScheme
import com.example.trustvault.ui.theme.LightModePrimaryGradient
import com.example.trustvault.ui.viewmodels.LoginScreenViewModel

class LoginActivity {
    @Composable // Composable object
    fun LoginScreen(
        darkTheme: Boolean,
        viewModel: LoginScreenViewModel = viewModel(),
        onGoBackClick: () -> Unit = {},
        onRegisterClick: () -> Unit = {}
    ) {
        val context = LocalContext.current
        Column (
            modifier = Modifier // Create this column and the attributes
                .fillMaxSize()
                .fillMaxHeight()// Make the layout fill all available space
                .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background),
            verticalArrangement = Arrangement.Center, // Vertical Alignment
            horizontalAlignment = Alignment.CenterHorizontally // Horizontal Alignment
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            // Go back icon
            Row (
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = if (darkTheme) painterResource(id = R.drawable.ic_go_back) else painterResource(id = R.drawable.ic_go_back_black),
                    contentDescription = "Go Back Button",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            onGoBackClick()
                        }
                )
            }

            // Title
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Bienvenido de nuevo",
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            listOf(Color(0xFFEB41EE), Color(0xFF6F82FF), Color(0xFFFFBB77))
                        ),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 200.dp, bottom = 32.dp)
                )
            }
            // Form Fields
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                // Username Input
                OutlinedTextField(
                    value = viewModel.username,
                    onValueChange = { viewModel.username = it },
                    label = { Text("Usuario") },
                    modifier = Modifier.fillMaxWidth(0.9f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF2F2F2),
                        unfocusedContainerColor = Color(0xFFF2F2F2),
                        disabledContainerColor = Color(0xFFF2F2F2),
                        focusedLabelColor = if (darkTheme) Color.White else Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                // Password Input
                OutlinedTextField(
                    value = viewModel.password,
                    onValueChange = { viewModel.password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(0.9f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF2F2F2),
                        unfocusedContainerColor = Color(0xFFF2F2F2),
                        disabledContainerColor = Color(0xFFF2F2F2),
                        focusedLabelColor = if (darkTheme) Color.White else Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button (
                onClick = {
                /* TODO: Search for user in the database, login and show home screen */
                    Toast.makeText(context, "Logged in user", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Transparent as it is the same color of the background
                contentPadding = PaddingValues(),
                enabled = viewModel.isFormValid
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            when {
                                darkTheme && viewModel.isFormValid -> DarkModePrimaryGradient
                                !darkTheme && viewModel.isFormValid -> LightModePrimaryGradient
                                else -> DisabledButtonGradient  // Greyed out when form is not filled out
                            },
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Continuar", color = Color.White, fontSize = 16.sp)
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
                        color = if (darkTheme) DarkColorScheme.onBackground else LightColorScheme.onBackground,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold

                    ),
                    modifier = Modifier
                        .padding(top = 250.dp, bottom = 16.dp)
                        .clickable {
                            onRegisterClick()
                        }
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "¿Has olvidado la contraseña?",
                    style = TextStyle(
                        color = if (darkTheme) DarkColorScheme.onBackground else LightColorScheme.onBackground,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .clickable {
                            /* TODO: Go to Lost password activity */
                            Toast.makeText(context, "Te pasa por cafre, cacho mamón.", Toast.LENGTH_SHORT).show()
                        }
                )
            }
        }
    }

}